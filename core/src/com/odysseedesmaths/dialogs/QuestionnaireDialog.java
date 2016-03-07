package com.odysseedesmaths.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.util.XMLSequencialReader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionnaireDialog extends DialogScreen {

    private QuestionnaireDialogReader reader;

    private static final int MAX_ANSWERS = 4;

    private Label questionTitle;
    private Container<CheckBox>[] answersContainers;
    private CheckBox[] answers;

    public QuestionnaireDialog(OdysseeDesMaths game, String questionnairePath) {
        super(game);

        setChar(Assets.ICON_HERO, 2);
        setChar(Assets.ICON_PYLES, 3);

        questionTitle = new Label("", new Label.LabelStyle(FONT_13, Color.FIREBRICK));
        questionTitle.setAlignment(Align.left);
        questionTitle.setWrap(true);

        CheckBox.CheckBoxStyle answerStyle = new CheckBox.CheckBoxStyle(skin.getDrawable("scroll_radio"), skin.getDrawable("scroll_radio_checked"), FONT_11, Color.BLACK);

        answers = new CheckBox[MAX_ANSWERS];
        for (int i=0; i < MAX_ANSWERS; i++) {
            answers[i] = new CheckBox("", answerStyle);
            answers[i].getImageCell().padRight(5);
            answers[i].getLabelCell().padTop(1);
        }
        answersContainers = new Container[MAX_ANSWERS];
        for (int i=0; i < MAX_ANSWERS; i++) {
            answersContainers[i] = new Container<>();
        }

        dialogTable.add(questionTitle).fill().padBottom(10).colspan(2).expandX().row();
        dialogTable.add(answersContainers[0]).padBottom(10);
        dialogTable.add(answersContainers[2]).padBottom(10).row();
        dialogTable.add(answersContainers[1]);
        dialogTable.add(answersContainers[3]);

        reader = this.new QuestionnaireDialogReader(questionnairePath);
    }

    public void setAnswers(int nbAnswer) {
        for (int i=0; i < MAX_ANSWERS; i++) {
            if (i < nbAnswer) answersContainers[i].setActor(answers[i]);
            else answersContainers[i].setActor(null);
        }
    }

    private class QuestionnaireDialogReader extends XMLSequencialReader {

        private static final String QUESTION_NODE = "question";
        private static final String ANSWER_NODE = "reponse";
        private static final String EXPLANATION_NODE = "explication";
        private static final String BACK_IMG_NODE = "background-image";
        private static final String MID_IMG_NODE = "middle-image";

        public QuestionnaireDialogReader(String xmlFilePath) {
            super(xmlFilePath);
        }

        @Override
        public Node initCurrentNode() {
            return goToNextSibling(document.getDocumentElement().getFirstChild(), QUESTION_NODE, true);
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
                        case QUESTION_NODE:
                            questionTitle.setText(element.getAttribute("intitule"));
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
                        case EXPLANATION_NODE:
                            break;
                    }
                    return nodeName;
                default:
                    return "";
            }
        }

        @Override
        public void next() {
            currentNode = goToNextSibling(currentNode, QUESTION_NODE, true);
        }

        @Override
        public boolean hasNext() {
            return goToNextSibling(currentNode, QUESTION_NODE, false) != null;
        }
    }

}
