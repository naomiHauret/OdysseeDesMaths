package com.odysseedesmaths;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DialogReader {

    private Document document;
    private Element currentElement;

    public DialogReader(String dialogPath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File dialogFile = new File(dialogPath);
        document = builder.parse(dialogFile);
        currentElement = (Element)document.getDocumentElement().getFirstChild();
    }

    public String getBackgroundImg() {
        return "";
    }

    public String getChar1() {
        return "";
    }

    public String getChar2() {
        return "";
    }

    public String getMiddleImg() {
        return "";
    }

    public String getText() {
        return "";
    }

    // Boutons ?

}
