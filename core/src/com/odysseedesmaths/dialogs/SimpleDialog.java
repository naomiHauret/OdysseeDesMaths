package com.odysseedesmaths.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.odysseedesmaths.*;
import com.odysseedesmaths.util.XMLSequencialReader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SimpleDialog extends DialogScreen {

    private static final int DISPLAY_SPEED = 13;

    private SimpleDialogReader reader;

    private Label dialogChar;
    private Label dialogText;
    private TemporalAction displayAction;
    private boolean textIsDisplayed;

    public SimpleDialog(OdysseeDesMaths game, String dialogPath) {
        super(game);

        dialogChar = new Label("", new Label.LabelStyle(FONT_15, Color.FIREBRICK));
        dialogChar.setAlignment(Align.left);
        dialogText = new Label("", new Label.LabelStyle(FONT_12, Color.BLACK));
        dialogText.setWrap(true);
        dialogText.setAlignment(Align.topLeft);

        dialogTable.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!textIsDisplayed) displayAction.finish();
                else if (reader.hasNext()) reader.next();
            }
        });
        dialogTable.add(dialogChar).fill().padBottom(10).row();
        dialogTable.add(dialogText).fill().expand();

        reader = this.new SimpleDialogReader(dialogPath);
    }

    public void setText(final String text) {
        TemporalAction action = new TemporalAction(text.length()/DISPLAY_SPEED) {
            @Override
            protected void update(float percent) {
                ((Label)actor).setText(text.substring(0, Math.round(text.length() * percent)));
            }

            @Override
            protected void begin() {
                super.begin();
                textIsDisplayed = false;
            }

            @Override
            protected void end() {
                super.end();
                textIsDisplayed = true;
            }
        };
        displayAction = action;
        dialogText.addAction(action);
    }

    private class SimpleDialogReader extends XMLSequencialReader {

        private static final String TEXT_NODE = "texte";
        private static final String CHAR_NODE = "personnage";
        private static final String BACK_IMG_NODE = "background-image";
        private static final String MID_IMG_NODE = "middle-image";

        public SimpleDialogReader(String dialogPath) {
            super(dialogPath);
        }

        @Override
        public Node initCurrentNode() {
            Node node = document.getDocumentElement().getFirstChild();
            Node tmpNode;
            do {
                node = goToNextSibling(node, CHAR_NODE, true);
                tmpNode = goToFirstChild(node, TEXT_NODE, true);
                System.out.println("init: "+node.getNodeName());
            } while (tmpNode == null);
            return tmpNode;
        }

        @Override
        public String process(Node node) {
            short nodeType = node.getNodeType();
            String nodeName = node.getNodeName();
            System.out.println("process: " + nodeName);

            switch (nodeType) {
                case Node.ELEMENT_NODE:
                    Element element = (Element)node;
                    switch (nodeName) {
                        case BACK_IMG_NODE:
                            setBackgroundImage(getAssetFor(element.getTextContent()));
                            break;
                        case MID_IMG_NODE:
                            setMiddleImage(getAssetFor(element.getTextContent()));
                            break;
                        case CHAR_NODE:
                            String perso = element.getAttribute("nom");
                            int position = Integer.valueOf(element.getAttribute("pos"));
                            setChar(getAssetForChar(perso), position);
                            if (perso.equals("hero")) {
                                dialogChar.setText(game.getSavesManager().getCurrentSave().getName().toUpperCase());
                            } else {
                                dialogChar.setText(perso.toUpperCase());
                            }
                            break;
                        case TEXT_NODE:
                            setText(format(element.getTextContent()));
                            break;
                    }
                    return nodeName;
                default:
                    return "";
            }
        }

        @Override
        public void next() {
            System.out.println("NEXT !");
            Node nextNode = goToNextSibling(currentNode, TEXT_NODE, true);

            if (nextNode == null) {
                // Le personnage a fini de parler, on passe au personnage suivant
                nextNode = goToNextSibling(currentNode.getParentNode(), CHAR_NODE, true);
                currentNode = goToFirstChild(nextNode, TEXT_NODE, true);
            } else {
                // On passe simplement au texte suivant
                currentNode = nextNode;
            }
        }

        @Override
        public boolean hasNext() {
            return goToNextSibling(currentNode, TEXT_NODE, false) != null
                    || goToNextSibling(currentNode.getParentNode(), CHAR_NODE, false) != null;
        }

    }
}
