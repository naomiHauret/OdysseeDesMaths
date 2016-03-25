package com.odysseedesmaths.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.odysseedesmaths.*;
import com.odysseedesmaths.util.XMLSequencialReader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SimpleDialog extends DialogScreen {

    private SimpleDialogReader reader;

    private Label dialogChar;
    private Label dialogText;

    public SimpleDialog(OdysseeDesMaths game, String dialogPath, final EndButtonsListener endButtonsListener) {
        super(game, endButtonsListener);

        dialogChar = new Label("", new Label.LabelStyle(FONT_16, Color.FIREBRICK));
        dialogChar.setAlignment(Align.left);
        dialogText = new Label("", new Label.LabelStyle(FONT_14, Color.BLACK));
        dialogText.setWrap(true);
        dialogText.setAlignment(Align.topLeft);

        dialogTable.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!displayAction.isFinished()) {
                    displayAction.finish();
                } else if (reader.hasNext()) {
                    reader.next();
                } else {
                    for (TextButton endButton : endButtonsList) {
                        buttonsGroup.addActor(endButton);
                    }
                }
            }
        });
        dialogTable.add(dialogChar).fill().padBottom(10).row();
        dialogTable.add(dialogText).fill().expand();

        reader = this.new SimpleDialogReader(dialogPath);
    }

    public void setText(final String text) {
        displayAction.reset();
        displayAction.setText(text);
        displayAction.setDuration(text.length()/DISPLAY_SPEED);
        dialogText.addAction(displayAction);
    }

    private class SimpleDialogReader extends XMLSequencialReader {

        private static final String TEXT_NODE = "text";
        private static final String CHAR_NODE = "personnage";
        private static final String BACK_IMG_NODE = "background-image";
        private static final String MID_IMG_NODE = "middle-image";
        private static final String CLEAR_NODE = "clear";
        private static final String END_BTN_NODE = "endbutton";

        public SimpleDialogReader(String dialogPath) {
            super(dialogPath);

            // Création des boutons de fin
            InputListener listener = new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    endButtonsListener.buttonPressed(event.getTarget().getName());
                }
            };

            Element buttonNode = (Element)goToFirstChild(document.getDocumentElement(), END_BTN_NODE, false);
            while (buttonNode != null) {
                TextButton button = new TextButton(buttonNode.getTextContent(), buttonStyle);
                button.setName(buttonNode.getAttribute("name"));
                button.addListener(listener);
                endButtonsList.add(button);
                buttonNode = (Element)goToNextSibling(buttonNode, END_BTN_NODE, false);
            }
        }

        @Override
        public Node initCurrentNode() {
            Node node = document.getDocumentElement().getFirstChild();
            Node tmpNode;
            do {
                node = goToNextSibling(node, CHAR_NODE, true);
                tmpNode = goToFirstChild(node, TEXT_NODE, true);
            } while (tmpNode == null);
            return tmpNode;
        }

        @Override
        public String process(Node node) {
            short nodeType = node.getNodeType();
            String nodeName = node.getNodeName();

            switch (nodeType) {
                case Node.ELEMENT_NODE:
                    Element element = (Element)node;
                    switch (nodeName) {
                        case BACK_IMG_NODE:
                            setBackgroundImage(getAssetFor(element.getAttribute("name")));
                            break;
                        case MID_IMG_NODE:
                            setMiddleImage(getAssetFor(element.getAttribute("name")));
                            break;
                        case CHAR_NODE:
                            String perso = element.getAttribute("name");
                            if (!element.hasAttribute("noimage")) {
                                setChar(getAssetForChar(perso), Integer.valueOf(element.getAttribute("pos")));
                            }
                            if (perso.equals("hero")) {
                                dialogChar.setText(game.getSavesManager().getCurrentSave().getName().toUpperCase());
                            } else {
                                dialogChar.setText(perso.toUpperCase());
                            }
                            break;
                        case TEXT_NODE:
                            setText(format(element.getTextContent()));
                            break;
                        case CLEAR_NODE:
                            switch (element.getAttribute("object")) {
                                case "middle-image":
                                    clearMiddleImage();
                                    break;
                                case "char":
                                    clearCharAt(Integer.valueOf(element.getAttribute("pos")));
                                    break;
                            }
                            break;
                    }
                    return nodeName;
                default:
                    return "";
            }
        }

        public void next() {
            Node nextNode = goToNextSibling(currentNode, TEXT_NODE, true);

            if (nextNode == null) {
                // Le personnage a fini de parler, on passe au personnage suivant
                Node charNode =  currentNode.getParentNode();
                do {
                    charNode = goToNextSibling(charNode, CHAR_NODE, true);
                    nextNode = goToFirstChild(charNode, TEXT_NODE, true);
                } while (nextNode == null);
            }

            currentNode = nextNode;
        }

        public boolean hasNext() {
            return goToNextSibling(currentNode, TEXT_NODE, false) != null
                    || goToNextSibling(currentNode.getParentNode(), CHAR_NODE, false) != null;
        }

    }
}