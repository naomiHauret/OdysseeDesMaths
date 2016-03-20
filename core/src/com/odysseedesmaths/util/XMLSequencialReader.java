package com.odysseedesmaths.util;

import com.badlogic.gdx.Gdx;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*
        Classe de lecture de fichier XML
 */

public abstract class XMLSequencialReader {

    protected Document document;
    protected Node currentNode;

    public XMLSequencialReader(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(Gdx.files.internal(xmlFilePath).read());
            currentNode = initCurrentNode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Node goToFirstChild(Node node, String targetNodeName, boolean processing) {
        Node result = node.getFirstChild();
        if (result == null) return null;
        String nodeName = processing ? process(result) : result.getNodeName();
        return nodeName.equals(targetNodeName) ? result : goToNextSibling(result, targetNodeName, processing);
    }

    public Node goToNextSibling(Node node, String targetNodeName, boolean processing) {
        Node result = node;
        String nodeName = "";
        do {
            result = result.getNextSibling();
            if (result != null) nodeName = processing ? process(result) : result.getNodeName();
        } while (result != null && !nodeName.equals(targetNodeName));
        return result;
    }

    public abstract Node initCurrentNode();
    public abstract String process(Node node);
}