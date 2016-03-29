package com.odysseedesmaths.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.util.XMLSequencialReader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
  classe d'affichage des questionnaires avec mise en place de la sélection multiple
*/

public class QuestionnaireDialog extends DialogScreen {

    private QuestionnaireDialogReader reader;

    private static final int MAX_ANSWERS = 4;

    private int nbQuestions = 0;
    private int nbGoodAnswers = 0;

    private Label dialogTitle;
    private Label dialogText;
    private InputListener textListener;
    private Container<CheckBox>[] answersContainers;
    private CheckBox[] answers;
    private TextButton validateButton;
    private TextButton nextQuestionButton;

    public QuestionnaireDialog(OdysseeDesMaths game, String questionnairePath) {
        super(game);

        setChar(Assets.ICON_HERO, 2);
        setChar(Assets.ICON_PYLES, 3);

        dialogTitle = new Label("", new Label.LabelStyle(FONT_13, Color.FIREBRICK));
        dialogTitle.setAlignment(Align.left);
        dialogTitle.setWrap(true);
        dialogText = new Label("", new Label.LabelStyle(FONT_11, Color.BLACK));
        dialogText.setAlignment(Align.left);
        dialogText.setWrap(true);

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

        dialogTable.add(dialogTitle).fill().padBottom(10).colspan(2).expandX().row();
        dialogTable.add(answersContainers[0]).padBottom(10);
        dialogTable.add(answersContainers[2]).padBottom(10).row();
        dialogTable.add(answersContainers[1]);
        dialogTable.add(answersContainers[3]);

        nextQuestionButton = new TextButton("Ok, question suivante !", buttonStyle);
        nextQuestionButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
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
                reader.processAnswer();
            }
        });

        textListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                reader.nextText();
            }
        };

        reader = this.new QuestionnaireDialogReader(questionnairePath);
    }

    /*
      mise en place des réponses possibles
    */
    public void setAnswers(int nbAnswer) {
        for (int i=0; i < MAX_ANSWERS; i++) {
            if (i < nbAnswer) answersContainers[i].setActor(answers[i]);
            else answersContainers[i].setActor(null);
        }
    }

    /*
      mise en place du texte
    */
    public void setText(final String text) {
        displayAction.reset();
        displayAction.setText(text);
        displayAction.setDuration(text.length()/DISPLAY_SPEED);
        dialogText.addAction(displayAction);
    }

    /*
      * Classe permettant de lire les éléments du questionnaires à partir d'un fichier XML
      */
      
    private class QuestionnaireDialogReader extends XMLSequencialReader {

        private static final String QUESTION_NODE = "question";
        private static final String ANSWER_NODE = "reponse";
        private static final String GOOD_ANSWER_NODE = "bonne-reponse";
        private static final String BAD_ANSWER_NODE = "mauvaise-reponse";
        private static final String TEXT_NODE = "text";
        private static final String BACK_IMG_NODE = "background-image";
        private static final String MID_IMG_NODE = "middle-image";

        private Node currentTextNode;

        public QuestionnaireDialogReader(String xmlFilePath) {
            super(xmlFilePath);
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
                            dialogTitle.setText("PYLES");
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
            do {
                if (answers[i].isChecked()) {
                    if (((Element)answer).getAttribute("valide").equals("true")) {
                        rightAnswer();
                    } else {
                        wrongAnswer();
                    }
                } else {
                    answer = goToNextSibling(answer, ANSWER_NODE, false);
                    i++;
                }
            } while (answer != null);
        }

        public void rightAnswer() {
            Node node = goToFirstChild(currentNode, GOOD_ANSWER_NODE, false);
            currentTextNode = goToFirstChild(node, TEXT_NODE, true);
        }

        public void wrongAnswer() {
            Node node = goToFirstChild(currentNode, BAD_ANSWER_NODE, false);
            currentTextNode = goToFirstChild(node, TEXT_NODE, true);
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
        }

        public boolean hasNextTest() {
            return goToNextSibling(currentTextNode, TEXT_NODE, false) != null;
        }
    }

}
