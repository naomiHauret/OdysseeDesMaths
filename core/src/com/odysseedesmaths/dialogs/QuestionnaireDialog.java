package com.odysseedesmaths.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.util.XMLSequencialReader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionnaireDialog extends DialogScreen {

    private enum Scene {QUESTION_SCENE, DIALOG_SCENE}

    private QuestionnaireDialogReader reader;

    private static final int MAX_ANSWERS = 4;

    private float nbQuestions = 0;
    private float nbGoodAnswers = 0;

    private Label dialogTitle;
    private Label dialogText;
    private Table answersTable;
    private Container<CheckBox>[] answersContainers;
    private CheckBox[] answers;
    private TextButton validateButton;
    private TextButton nextQuestionButton;

    public QuestionnaireDialog(OdysseeDesMaths game, String questionnairePath, EndButtonsListener endButtonsListener) {
        super(game, endButtonsListener);

        setChar(Assets.ICON_HERO, 2);
        setChar(Assets.ICON_PYLES, 3);

        dialogTitle = new Label("", new Label.LabelStyle(FONT_13, Color.FIREBRICK));
        dialogTitle.setAlignment(Align.left);
        dialogTitle.setWrap(true);
        dialogText = new Label("", new Label.LabelStyle(FONT_11, Color.BLACK));
        dialogText.setAlignment(Align.topLeft);
        dialogText.setWrap(true);
        dialogText.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!displayAction.isFinished()) {
                    displayAction.finish();
                } else if (reader.hasNextText()) {
                    reader.nextText();
                } else if (reader.hasNextQuestion()) {
                    if (buttonsGroup.findActor("nextQuestionButton") == null) {
                        buttonsGroup.addActor(nextQuestionButton);
                    }
                } else {
                    // fin du questionnaire
                    for (TextButton endButton : endButtonsList) {
                        buttonsGroup.addActor(endButton);
                    }
                }
            }
        });

        CheckBox.CheckBoxStyle answerStyle = new CheckBox.CheckBoxStyle(skin.getDrawable("scroll_radio"), skin.getDrawable("scroll_radio_checked"), FONT_11, Color.BLACK);

        answers = new CheckBox[MAX_ANSWERS];
        InputListener listener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int i=0;
                boolean trouve = false;
                // On cherche laquel des réponses a été sélectionnée
                while (i<MAX_ANSWERS && !trouve) {
                    if (answers[i] == event.getTarget()) {
                        trouve = true;
                    } else {
                        i++;
                    }
                }
                // On la coche et décoche les autres
                for (int j=0; j < MAX_ANSWERS; j++) {
                    if (j!=i) answers[j].setChecked(false);
                }
                // On affiche le bouton de validation si ce n'est pas déjà fait
                if (buttonsGroup.findActor("validateButton") == null) {
                    buttonsGroup.addActor(validateButton);
                }
                return false;
            }
        };
        for (int i=0; i < MAX_ANSWERS; i++) {
            answers[i] = new CheckBox("", answerStyle);
            answers[i].getImageCell().padRight(5);
            answers[i].getLabelCell().padTop(1);
            answers[i].addListener(listener);
        }
        answersContainers = new Container[MAX_ANSWERS];
        for (int i=0; i < MAX_ANSWERS; i++) {
            answersContainers[i] = new Container<>();
        }

        nextQuestionButton = new TextButton("Ok, question suivante !", buttonStyle);
        nextQuestionButton.setName("nextQuestionButton");
        nextQuestionButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setScene(Scene.QUESTION_SCENE);
                reader.nextQuestion();
            }
        });
        validateButton = new TextButton("C'est mon dernier mot", buttonStyle);
        validateButton.setName("validateButton");
        validateButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setScene(Scene.DIALOG_SCENE);
                reader.processAnswer();
            }
        });

        answersTable = new Table();
        answersTable.add(answersContainers[0]).expand();
        answersTable.add(answersContainers[2]).expand().row();
        answersTable.add(answersContainers[1]).expand();
        answersTable.add(answersContainers[3]).expand();

        setScene(Scene.QUESTION_SCENE);

        reader = this.new QuestionnaireDialogReader(questionnairePath);
    }

    public void setAnswers(int nbAnswer) {
        for (int i=0; i < MAX_ANSWERS; i++) {
            if (i < nbAnswer) {
                answersContainers[i].setActor(answers[i]);
            } else {
                answersContainers[i].setActor(null);
            }
        }
    }

    public void setText(final String text) {
        displayAction.reset();
        displayAction.setText(text);
        displayAction.setDuration(text.length()/DISPLAY_SPEED);
        dialogText.addAction(displayAction);
    }

    public void setScene(Scene scene) {
        dialogTable.clear();
        buttonsGroup.clear();
        dialogTable.add(dialogTitle).fill().padBottom(10).row();
        switch (scene) {
            case QUESTION_SCENE:
                dialogTable.add(answersTable).fill().expand();
                break;
            case DIALOG_SCENE:
                dialogTable.add(dialogText).fill().expand();
                dialogTitle.setText("PYLES");
                break;
        }
    }

    private class QuestionnaireDialogReader extends XMLSequencialReader {

        private static final String QUESTION_NODE = "question";
        private static final String ANSWER_NODE = "reponse";
        private static final String GOOD_ANSWER_NODE = "bonne-reponse";
        private static final String BAD_ANSWER_NODE = "mauvaise-reponse";
        private static final String TEXT_NODE = "text";
        private static final String BACK_IMG_NODE = "background-image";
        private static final String MID_IMG_NODE = "middle-image";
        private static final String END_BTN_NODE = "endbutton";

        private Node currentTextNode;

        public QuestionnaireDialogReader(String xmlFilePath) {
            super(xmlFilePath);

            // Création des boutons de fin
            InputListener listener = new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    endButtonsListener.buttonPressed(event.getTarget().getName(), nbGoodAnswers/nbQuestions);
                }
            };

            Element buttonNode = (Element)goToFirstChild(document.getDocumentElement(), END_BTN_NODE, false);
            do {
                TextButton button = new TextButton(buttonNode.getTextContent(), buttonStyle);
                button.setName(buttonNode.getAttribute("name"));
                button.addListener(listener);
                endButtonsList.add(button);
                buttonNode = (Element)goToNextSibling(buttonNode, END_BTN_NODE, false);
            } while (buttonNode != null);
        }

        @Override
        public Node initCurrentNode() {
            return goToFirstChild(document.getDocumentElement(), QUESTION_NODE, true);
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
                            setBackgroundImage(getAssetFor(element.getTextContent()));
                            break;
                        case MID_IMG_NODE:
                            setMiddleImage(getAssetFor(element.getTextContent()));
                            break;
                        case QUESTION_NODE:
                            dialogTitle.setText(element.getAttribute("intitule"));
                            NodeList childs = element.getChildNodes();
                            int nbAnswer = 0;
                            for (int i=0; i<childs.getLength(); i++) {
                                Node child = childs.item(i);
                                if (child.getNodeName().equals(ANSWER_NODE)) {
                                    answers[nbAnswer].setText(child.getTextContent());
                                    nbAnswer++;
                                }
                            }
                            setAnswers(nbAnswer);
                            break;
                        case ANSWER_NODE:
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

        public void processAnswer() {
            int i=0;
            Node answer = goToFirstChild(currentNode, ANSWER_NODE, false);
            nbQuestions++;
            Node nextNode = null;
            do {
                if (answers[i].isChecked()) {
                    if (((Element)answer).getAttribute("valide").equals("true")) {
                        nbGoodAnswers++;
                        nextNode = goToFirstChild(currentNode, GOOD_ANSWER_NODE, false);
                    } else {
                        nextNode = goToFirstChild(currentNode, BAD_ANSWER_NODE, false);
                    }
                    answers[i].setChecked(false);
                } else {
                    answer = goToNextSibling(answer, ANSWER_NODE, false);
                    i++;
                }
            } while (answer != null && nextNode == null);

            currentTextNode = goToFirstChild(nextNode, TEXT_NODE, true);
        }

        public void nextQuestion() {
            currentNode = goToNextSibling(currentNode, QUESTION_NODE, true);
        }

        public boolean hasNextQuestion() {
            return goToNextSibling(currentNode, QUESTION_NODE, false) != null;
        }

        public void nextText() {
            Node node = goToNextSibling(currentTextNode, TEXT_NODE, true);
            if (node == null) {
                node = goToFirstChild(currentNode, TEXT_NODE, false);
                process(node);
            }
            currentTextNode = node;
        }

        public boolean hasNextText() {
            boolean hasNext = goToNextSibling(currentTextNode, TEXT_NODE, false) != null;
            if (!hasNext) {
                hasNext = currentTextNode.getParentNode().getNodeName().equals(QUESTION_NODE);
                if (hasNext) hasNext = goToFirstChild(currentNode, TEXT_NODE, false) != null;
            }
            return hasNext;
        }
    }

}
