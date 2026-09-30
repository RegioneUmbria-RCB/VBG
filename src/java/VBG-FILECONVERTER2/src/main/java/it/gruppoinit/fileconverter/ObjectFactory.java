//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2021.02.05 alle 03:23:29 PM CET 
//


package it.gruppoinit.fileconverter;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.fileconverter package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.fileconverter
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConvertRequest }
     * 
     */
    public ConvertRequest createConvertRequest() {
        return new ConvertRequest();
    }

    /**
     * Create an instance of {@link ConvertResponse }
     * 
     */
    public ConvertResponse createConvertResponse() {
        return new ConvertResponse();
    }

    /**
     * Create an instance of {@link ConvertBinaryRequest }
     * 
     */
    public ConvertBinaryRequest createConvertBinaryRequest() {
        return new ConvertBinaryRequest();
    }

    /**
     * Create an instance of {@link ConvertBinaryResponse }
     * 
     */
    public ConvertBinaryResponse createConvertBinaryResponse() {
        return new ConvertBinaryResponse();
    }

    /**
     * Create an instance of {@link MergeDataRequest }
     * 
     */
    public MergeDataRequest createMergeDataRequest() {
        return new MergeDataRequest();
    }

    /**
     * Create an instance of {@link MergeDataResponse }
     * 
     */
    public MergeDataResponse createMergeDataResponse() {
        return new MergeDataResponse();
    }

    /**
     * Create an instance of {@link MergeDataAndConvertRequest }
     * 
     */
    public MergeDataAndConvertRequest createMergeDataAndConvertRequest() {
        return new MergeDataAndConvertRequest();
    }

    /**
     * Create an instance of {@link MergeDataAndConvertResponse }
     * 
     */
    public MergeDataAndConvertResponse createMergeDataAndConvertResponse() {
        return new MergeDataAndConvertResponse();
    }

    /**
     * Create an instance of {@link MergeAndConvertRequest }
     * 
     */
    public MergeAndConvertRequest createMergeAndConvertRequest() {
        return new MergeAndConvertRequest();
    }

    /**
     * Create an instance of {@link MergeAndConvertResponse }
     * 
     */
    public MergeAndConvertResponse createMergeAndConvertResponse() {
        return new MergeAndConvertResponse();
    }

}
