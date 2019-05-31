package data;

import exceptions.WrongConfigFileException;
import logic.membership.AbsoluteQuantifier;
import logic.membership.MembershipFunction;
import logic.membership.Quantifier;
import logic.membership.RelativeQuantifier;
import logic.membership.Summarizer;
import logic.membership.TrapezoidFunction;
import logic.membership.TriangularFunction;
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

    public List<Summarizer> getSummarizers(String path) {

        List<Summarizer> result = new ArrayList<>();

        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fXmlFile);

            NodeList nodeList = ((Element)document.getElementsByTagName("summarizers").item(0))
                    .getElementsByTagName("summarizer");

            for (int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element summarizerElement = (Element) node;

                MembershipFunction membershipFunction = getMembershipFunction(summarizerElement);


                Summarizer summarizer = new Summarizer(summarizerElement.getAttribute("name"),
                        summarizerElement.getAttribute("attribute"),
                        membershipFunction
                );
                result.add(summarizer);
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

                MembershipFunction membershipFunction = getMembershipFunction(quantifierElement);

                String quantifierType = quantifierElement.getAttribute("quantifierType");

                Quantifier quantifier;

                if("relative".equals(quantifierType)) {
                    quantifier = new RelativeQuantifier();
                    quantifier.setLabel(quantifierElement.getAttribute("name"));
                    quantifier.setMembershipFunction(membershipFunction);
                }
                else if("absolute".equals(quantifierType)) {
                    quantifier = new AbsoluteQuantifier();
                    quantifier.setLabel(quantifierElement.getAttribute("name"));
                    quantifier.setMembershipFunction(membershipFunction);
                } else throw new WrongConfigFileException("Unknown quantifier type");
                result.add(quantifier);
            }
        } catch (ParserConfigurationException | WrongConfigFileException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return result;
    }

    private MembershipFunction getMembershipFunction(Element membershipFunctionElement)
            throws WrongConfigFileException {
        MembershipFunction membershipFunction;
        String type = membershipFunctionElement.getAttribute("type");
        if("trapezoid".equals(type)) {
            membershipFunction = new TrapezoidFunction(
                    Double.parseDouble(membershipFunctionElement.getAttribute("a1")),
                    Double.parseDouble(membershipFunctionElement.getAttribute("b1")),
                    Double.parseDouble(membershipFunctionElement.getAttribute("b2")),
                    Double.parseDouble(membershipFunctionElement.getAttribute("a2")));
        }
        else if("triangular".equals(type)) {
            membershipFunction = new TriangularFunction(
                    Double.parseDouble(membershipFunctionElement.getAttribute("a1")),
                    Double.parseDouble(membershipFunctionElement.getAttribute("a2")),
                    Double.parseDouble(membershipFunctionElement.getAttribute("a3")));
        } else throw new WrongConfigFileException("Unknown membership function type");
        return membershipFunction;
    }

}
