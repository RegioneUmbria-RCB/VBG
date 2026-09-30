/*
 * Copyright 2004 - 2012 Mirko Nasato and contributors 2016 - 2020 Simon Braconnier and contributors
 *
 * This file is part of JODConverter - Java OpenDocument Converter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package it.paevolution.fileconverter2.controller;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.util.FileUtils;
import org.jodconverter.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.fileconverter.MergeAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataResponse;
import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;
import it.paevolution.fileconverter2.service.BaseService.ModelTypesEnum;
import it.paevolution.fileconverter2.service.FileConverterService;
import it.paevolution.fileconverter2.service.MergeService;
import it.paevolution.fileconverter2.util.Utils;

/** Controller providing conversion endpoints. */
@Controller
public class ConverterController {

    private static final String ATTRNAME_ERROR_MESSAGE = "errorMessage";
    private static final String ON_ERROR_REDIRECT = "redirect:/";
    @Autowired
    private FileConverterService fileConverterService;
    @Autowired
    private MergeService mergeService;

    // @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    /* default */ String index(Model model) {

	model.addAttribute("activeTab", "convert");
	return "converter";
    }

    @PostMapping("/convert")
    /* default */ Object convert(@RequestParam("htmlContent") final String inputFile,
	    @RequestParam(name = "outputFormat0", required = false) final String outputFormat, final RedirectAttributes redirectAttributes) {

	if (inputFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please insert html.");
	    return ON_ERROR_REDIRECT;
	}
	if (StringUtils.isBlank(outputFormat)) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select an output format.");
	    return ON_ERROR_REDIRECT;
	}
	try {
	    final DocumentFormat targetFormat = DefaultDocumentFormatRegistry.getFormatByExtension(outputFormat);
	    Assert.notNull(targetFormat, "targetFormat must not be null");
	    ConversionsSupportedEnum conversionType = ConversionsSupportedEnum.valueOf(outputFormat.toUpperCase());
	    ConvertResponse resp = fileConverterService.convertStringContent(inputFile, FileInTypesEnum.HTML, conversionType);
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType(resp.getMimeType()));
	    headers.add("Content-Disposition",
		    "attachment; filename=" + FileUtils.getBaseName(resp.getFileName()) + "." + targetFormat.getExtension());
	    return new ResponseEntity<>(resp.getBinaryData(), headers, HttpStatus.OK);
	} catch (Exception e) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Could not convert the content. Cause: " + e.getMessage());
	}
	return ON_ERROR_REDIRECT;
    }

    /**
     * Converts a source file to a target format.
     *
     * @param inputFile
     *            Source file to convert.
     * @param outputFormat
     *            Output format of the conversion.
     * @param redirectAttributes
     *            Model that contains attributes
     * @return The converted file, or the error redirection if an error occurs.
     */
    @PostMapping("/convertbinary")
    /* default */ Object convertbinary(@RequestParam final MultipartFile inputFile, @RequestParam(required = false) final String outputFormat,
	    final RedirectAttributes redirectAttributes) {

	if (inputFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select a file to upload.");
	    return ON_ERROR_REDIRECT;
	}
	if (StringUtils.isBlank(outputFormat)) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select an output format.");
	    return ON_ERROR_REDIRECT;
	}
	// Here, we could have a dedicated service that would convert document
	try {
	    final DocumentFormat targetFormat = DefaultDocumentFormatRegistry.getFormatByExtension(outputFormat);
	    Assert.notNull(targetFormat, "targetFormat must not be null");
	    String originalFilename = inputFile.getOriginalFilename();
	    FileInTypesEnum tipoFile = Utils.getFileInTypesFromFileName(originalFilename);
	    ConversionsSupportedEnum tipoConversione = Utils.getConversionsSupportedFromFileName(outputFormat);
	    ConvertResponse content = fileConverterService.convertBinaryContent(IOUtils.toByteArray(inputFile.getInputStream()), tipoFile,
		    tipoConversione);
	    // converter.convert(inputFile.getInputStream()).to(baos).as(targetFormat).execute();
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType(targetFormat.getMediaType()));
	    headers.add("Content-Disposition", "attachment; filename=" + FileUtils.getBaseName(originalFilename) + "." + targetFormat.getExtension());
	    return new ResponseEntity<>(content.getBinaryData(), headers, HttpStatus.OK);
	} catch (IOException e) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE,
		    "Could not convert the file " + inputFile.getOriginalFilename() + ". Cause: " + e.getMessage());
	}
	return ON_ERROR_REDIRECT;
    }

    public static void main(String[] args) {

	System.out.println(DefaultDocumentFormatRegistry.getFormatByExtension("xlsx"));
    }

    @PostMapping("/merge")
    /* default */ Object merge(@RequestParam("inputFileRTF") final MultipartFile inputRTFFile,
	    @RequestParam("inputFileXML") final MultipartFile inputXMLFile, final RedirectAttributes redirectAttributes) {

	if (inputRTFFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select a RTF file to upload.");
	    return ON_ERROR_REDIRECT;
	}
	if (inputXMLFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select a XML file to upload.");
	    return ON_ERROR_REDIRECT;
	}
	try {
	    MergeDataResponse resp = mergeService.mergeData(inputRTFFile.getBytes(), inputXMLFile.getBytes());
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType(resp.getMimeType()));
	    headers.add("Content-Disposition", "attachment; filename=" + resp.getFileName());
	    return new ResponseEntity<>(resp.getBinaryData(), headers, HttpStatus.OK);
	} catch (IOException e) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE,
		    "Could not merge the file " + inputRTFFile.getOriginalFilename() + ". Cause: " + e.getMessage());
	}
	return ON_ERROR_REDIRECT;
    }

    @PostMapping("/mergeexport")
    /* default */ Object mergeexport(@RequestParam("inputFileRTF1") final MultipartFile inputRTFFile,
	    @RequestParam("inputFileXML1") final MultipartFile inputXMLFile, @RequestParam(name = "outputFormat1") final String outputFormat,
	    final RedirectAttributes redirectAttributes) {

	if (inputRTFFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select a RTF file to upload.");
	    return ON_ERROR_REDIRECT;
	}
	if (inputXMLFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select a XML file to upload.");
	    return ON_ERROR_REDIRECT;
	}
	ConversionsSupportedEnum conversionType = ConversionsSupportedEnum.valueOf(outputFormat.toUpperCase());
	try {
	    MergeDataAndConvertResponse resp = mergeService.mergeDataAndExport(inputRTFFile.getBytes(), inputXMLFile.getBytes(), conversionType);
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType(resp.getMimeType()));
	    headers.add("Content-Disposition", "attachment; filename=" + resp.getFileName());
	    return new ResponseEntity<>(resp.getBinaryData(), headers, HttpStatus.OK);
	} catch (IOException e) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE,
		    "Could not merge the file " + inputRTFFile.getOriginalFilename() + ". Cause: " + e.getMessage());
	}
	return ON_ERROR_REDIRECT;
    }

    @PostMapping("/mergeconvert")
    /* default */ Object mergeconvert(@RequestParam("inputFileXSL2") final MultipartFile inputXSLFile,
	    @RequestParam("inputFileXML2") final MultipartFile inputXMLFile, @RequestParam(name = "outputFormat2") final String outputFormat,
	    final RedirectAttributes redirectAttributes) {

	if (inputXSLFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select a XSL file to upload.");
	    return ON_ERROR_REDIRECT;
	}
	if (inputXMLFile.isEmpty()) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE, "Please select a XML file to upload.");
	    return ON_ERROR_REDIRECT;
	}
	ConversionsSupportedEnum conversionType = ConversionsSupportedEnum.valueOf(outputFormat.toUpperCase());
	try {
	    MergeAndConvertResponse resp = mergeService.mergeAndConvert(inputXMLFile.getBytes(), FileInTypesEnum.XML, inputXSLFile.getBytes(),
		    ModelTypesEnum.XSL, conversionType);
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType(resp.getMimeType()));
	    headers.add("Content-Disposition", "attachment; filename=" + resp.getFileName());
	    return new ResponseEntity<>(resp.getBinaryData(), headers, HttpStatus.OK);
	} catch (IOException e) {
	    redirectAttributes.addFlashAttribute(ATTRNAME_ERROR_MESSAGE,
		    "Could not merge the file " + inputXMLFile.getOriginalFilename() + ". Cause: " + e.getMessage());
	}
	return ON_ERROR_REDIRECT;
    }
}
