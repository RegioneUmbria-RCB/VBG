package it.gruppoinit.pal.gp.core.utils;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Delegating {@link XMLStreamWriter} that filters out UTF-8 characters that are illegal in XML.
 * 
 * @author Erik van Zijst
 */
public class EscapingXMLStreamWriter implements XMLStreamWriter {

    private final XMLStreamWriter writer;
    public static final char substitute = '\uFFFD';
    private static final Set<Character> illegalChars;
    static {
	illegalChars = new HashSet<Character>();
	illegalChars.add(new Character('\u0000'));
	illegalChars.add(new Character('\u0001'));
	illegalChars.add(new Character('\u0002'));
	illegalChars.add(new Character('\u0003'));
	illegalChars.add(new Character('\u0004'));
	illegalChars.add(new Character('\u0005'));
	illegalChars.add(new Character('\u0006'));
	illegalChars.add(new Character('\u0007'));
	illegalChars.add(new Character('\u0008'));
	illegalChars.add(new Character('\u000B'));
	illegalChars.add(new Character('\u000C'));
	illegalChars.add(new Character('\u000E'));
	illegalChars.add(new Character('\u000F'));
	illegalChars.add(new Character('\u0010'));
	illegalChars.add(new Character('\u0011'));
	illegalChars.add(new Character('\u0012'));
	illegalChars.add(new Character('\u0013'));
	illegalChars.add(new Character('\u0014'));
	illegalChars.add(new Character('\u0015'));
	illegalChars.add(new Character('\u0016'));
	illegalChars.add(new Character('\u0017'));
	illegalChars.add(new Character('\u0018'));
	illegalChars.add(new Character('\u0019'));
	illegalChars.add(new Character('\u001A'));
	illegalChars.add(new Character('\u001B'));
	illegalChars.add(new Character('\u001C'));
	illegalChars.add(new Character('\u001D'));
	illegalChars.add(new Character('\u001E'));
	illegalChars.add(new Character('\u001F'));
	illegalChars.add(new Character('\uFFFE'));
	illegalChars.add(new Character('\uFFFF'));
    }

    public EscapingXMLStreamWriter(XMLStreamWriter writer) {

	if (null == writer) {
	    throw new IllegalArgumentException("null");
	} else {
	    this.writer = writer;
	}
    }

    private boolean isIllegal(char c) {

	return illegalChars.contains(c);
    }

    /**
     * Substitutes all illegal characters in the given string by the value of {@link EscapingXMLStreamWriter#substitute}
     * . If no illegal characters were found, no copy is made and the given string is returned.
     * 
     * @param string
     * @return
     */
    private String escapeCharacters(String string) {

	char[] copy = null;
	boolean copied = false;
	for (int i = 0; i < string.length(); i++) {
	    if (isIllegal(string.charAt(i))) {
		if (!copied) {
		    copy = string.toCharArray();
		    copied = true;
		}
		copy[i] = substitute;
	    }
	}
	return copied ? new String(copy) : string;
    }

    public void writeStartElement(String s) throws XMLStreamException {

	writer.writeStartElement(s);
    }

    public void writeStartElement(String s, String s1) throws XMLStreamException {

	writer.writeStartElement(s, s1);
    }

    public void writeStartElement(String s, String s1, String s2) throws XMLStreamException {

	writer.writeStartElement(s, s1, s2);
    }

    public void writeEmptyElement(String s, String s1) throws XMLStreamException {

	writer.writeEmptyElement(s, s1);
    }

    public void writeEmptyElement(String s, String s1, String s2) throws XMLStreamException {

	writer.writeEmptyElement(s, s1, s2);
    }

    public void writeEmptyElement(String s) throws XMLStreamException {

	writer.writeEmptyElement(s);
    }

    public void writeEndElement() throws XMLStreamException {

	writer.writeEndElement();
    }

    public void writeEndDocument() throws XMLStreamException {

	writer.writeEndDocument();
    }

    public void close() throws XMLStreamException {

	writer.close();
    }

    public void flush() throws XMLStreamException {

	writer.flush();
    }

    public void writeAttribute(String localName, String value) throws XMLStreamException {

	writer.writeAttribute(localName, escapeCharacters(value));
    }

    public void writeAttribute(String prefix, String namespaceUri, String localName, String value) throws XMLStreamException {

	writer.writeAttribute(prefix, namespaceUri, localName, escapeCharacters(value));
    }

    public void writeAttribute(String namespaceUri, String localName, String value) throws XMLStreamException {

	writer.writeAttribute(namespaceUri, localName, escapeCharacters(value));
    }

    public void writeNamespace(String s, String s1) throws XMLStreamException {

	writer.writeNamespace(s, s1);
    }

    public void writeDefaultNamespace(String s) throws XMLStreamException {

	writer.writeDefaultNamespace(s);
    }

    public void writeComment(String s) throws XMLStreamException {

	writer.writeComment(s);
    }

    public void writeProcessingInstruction(String s) throws XMLStreamException {

	writer.writeProcessingInstruction(s);
    }

    public void writeProcessingInstruction(String s, String s1) throws XMLStreamException {

	writer.writeProcessingInstruction(s, s1);
    }

    public void writeCData(String s) throws XMLStreamException {

	writer.writeCData(escapeCharacters(s));
    }

    public void writeDTD(String s) throws XMLStreamException {

	writer.writeDTD(s);
    }

    public void writeEntityRef(String s) throws XMLStreamException {

	writer.writeEntityRef(s);
    }

    public void writeStartDocument() throws XMLStreamException {

	writer.writeStartDocument();
    }

    public void writeStartDocument(String s) throws XMLStreamException {

	writer.writeStartDocument(s);
    }

    public void writeStartDocument(String s, String s1) throws XMLStreamException {

	writer.writeStartDocument(s, s1);
    }

    public void writeCharacters(String s) throws XMLStreamException {

	writer.writeCharacters(escapeCharacters(s));
    }

    public void writeCharacters(char[] chars, int start, int len) throws XMLStreamException {

	writer.writeCharacters(escapeCharacters(new String(chars, start, len)));
    }

    public String getPrefix(String s) throws XMLStreamException {

	return writer.getPrefix(s);
    }

    public void setPrefix(String s, String s1) throws XMLStreamException {

	writer.setPrefix(s, s1);
    }

    public void setDefaultNamespace(String s) throws XMLStreamException {

	writer.setDefaultNamespace(s);
    }

    public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {

	writer.setNamespaceContext(namespaceContext);
    }

    public NamespaceContext getNamespaceContext() {

	return writer.getNamespaceContext();
    }

    public Object getProperty(String s) throws IllegalArgumentException {

	return writer.getProperty(s);
    }
}
