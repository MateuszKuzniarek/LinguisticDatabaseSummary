package data;

import exceptions.WrongConfigFileException;
import logic.membership.FuzzySet;
import logic.summaries.Quantifier;
import logic.summaries.LinguisticVariable;
import logic.membership.TrapezoidFuzzySet;
import logic.membership.TriangularFuzzySet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlLoader {

    public List<LinguisticVariable> getLinguisticVariables(String path) {

        List<LinguisticVariable> result = new ArrayList<>();

        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fXmlFile);

            NodeList nodeList = ((Element)document.getElementsByTagName("linguisticVariables").item(0))
                    .getElementsByTagName("linguisticVariable");

            for (int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element linguisticVariableElement = (Element) node;

                FuzzySet fuzzySet = getFuzzySet(linguisticVariableElement);


                LinguisticVariable linguisticVariable = new LinguisticVariable(
                        linguisticVariableElement.getAttribute("name"),
                        linguisticVariableElement.getAttribute("attribute"),
                        fuzzySet);
                result.add(linguisticVariable);
            }
        } catch (ParserConfigurationException | WrongConfigFileException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Quantifier> getQuantifiers(String path) {

        List<Quantifier> result = new ArrayList<>();

        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fXmlFile);

            NodeList nodeList = ((Element)document.getElementsByTagName("quantifiers").item(0))
                    .getElementsByTagName("quantifier");

            for (int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element quantifierElement = (Element) node;

                FuzzySet fuzzySet = getFuzzySet(quantifierElement);

                String quantifierType = quantifierElement.getAttribute("quantifierType");

                Quantifier quantifier;

                quantifier = new Quantifier();
                quantifier.setLabel(quantifierElement.getAttribute("name"));
                quantifier.setFuzzySet(fuzzySet);
                if("relative".equals(quantifierType)) {
                    quantifier.setRelative(true);
                } else if ("absolute".equals(quantifierType)) {
                    quantifier.setRelative(false);
                } else throw new WrongConfigFileException("Unknown quantifier type");
                result.add(quantifier);
            }
        } catch (ParserConfigurationException | WrongConfigFileException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return result;
    }

    private FuzzySet getFuzzySet(Element fuzzySetElement)
            throws WrongConfigFileException {
        FuzzySet fuzzySet;
        String type = fuzzySetElement.getAttribute("type");
        if("trapezoid".equals(type)) {
            fuzzySet = new TrapezoidFuzzySet(
                    Double.parseDouble(fuzzySetElement.getAttribute("a1")),
                    Double.parseDouble(fuzzySetElement.getAttribute("b1")),
                    Double.parseDouble(fuzzySetElement.getAttribute("b2")),
                    Double.parseDouble(fuzzySetElement.getAttribute("a2")));
        }
        else if("triangular".equals(type)) {
            fuzzySet = new TriangularFuzzySet(
                    Double.parseDouble(fuzzySetElement.getAttribute("a1")),
                    Double.parseDouble(fuzzySetElement.getAttribute("a2")),
                    Double.parseDouble(fuzzySetElement.getAttribute("a3")));
        } else throw new WrongConfigFileException("Unknown membership function type");
        fuzzySet
                .setRealmStart(Double.parseDouble(fuzzySetElement.getAttribute("realm-start")));
        fuzzySet
                .setRealmEnd(Double.parseDouble(fuzzySetElement.getAttribute("realm-end")));

        return fuzzySet;
    }

}
