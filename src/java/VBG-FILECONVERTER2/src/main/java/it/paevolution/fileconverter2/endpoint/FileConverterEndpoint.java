package it.paevolution.fileconverter2.endpoint;

import org.jodconverter.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.fileconverter.ConvertRequest;
import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.fileconverter.MergeAndConvertRequest;
import it.gruppoinit.fileconverter.MergeAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataAndConvertRequest;
import it.gruppoinit.fileconverter.MergeDataAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataRequest;
import it.gruppoinit.fileconverter.MergeDataResponse;
import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;
import it.paevolution.fileconverter2.service.BaseService.ModelTypesEnum;
import it.paevolution.fileconverter2.service.FileConverterService;
import it.paevolution.fileconverter2.service.MergeService;
import it.paevolution.fileconverter2.util.Utils;

@Endpoint
public class FileConverterEndpoint {

	@Autowired
	private FileConverterService fileConverterService;
	@Autowired
	private MergeService mergeService;

	private FileInTypesEnum contentTypeEnum;
	private ConversionsSupportedEnum conversionTypeEnum;
	private static final String MESSAGES_NAMESPACE = "http://gruppoinit.it/fileconverter";
	private static final String CONVERT_MESSAGE = "ConvertRequest";
	private static final String CONVERT_BINARY_MESSAGE = "ConvertBinaryRequest";
	private static final String MERGE_DATA_MESSAGE = "MergeDataRequest";
	private static final String MERGE_DATA_AND_EXPORT_MESSAGE = "MergeDataAndConvertRequest";
	private static final String MERGE_AND_CONVERT_MESSAGE = "MergeAndConvertRequest";

	@PayloadRoot(localPart = CONVERT_MESSAGE, namespace = MESSAGES_NAMESPACE)
	@ResponsePayload
	public ConvertResponse convert(@RequestPayload ConvertRequest request) {

		String contentType = request.getContentType();
		String conversionType = request.getConversionType();
		this.conversionTypeEnum = Utils.getDescrizioneConversionsSupportedEnum(conversionType);
		this.contentTypeEnum = Utils.getDescrizioneFileInTypesEnum(contentType);
		return fileConverterService.convertStringContent(request.getContent(), this.contentTypeEnum,
				this.conversionTypeEnum);
	}

	@PayloadRoot(localPart = CONVERT_BINARY_MESSAGE, namespace = MESSAGES_NAMESPACE)
	@ResponsePayload
	public ConvertBinaryResponse convertBinary(@RequestPayload ConvertBinaryRequest request) {

		String contentType = request.getContentType();
		String conversionType = request.getConversionType();
		this.conversionTypeEnum = Utils.getDescrizioneConversionsSupportedEnum(conversionType);
		this.contentTypeEnum = Utils.getDescrizioneFileInTypesEnum(contentType);
		ConvertResponse cResponse = fileConverterService.convertBinaryContent(request.getBinaryData(),
				this.contentTypeEnum, this.conversionTypeEnum);
		ConvertBinaryResponse response = new ConvertBinaryResponse();
		response.setBinaryData(cResponse.getBinaryData());
		response.setFileName(cResponse.getFileName());
		response.setMimeType(cResponse.getMimeType());
		return response;
	}

	@PayloadRoot(localPart = MERGE_DATA_MESSAGE, namespace = MESSAGES_NAMESPACE)
	@ResponsePayload
	public MergeDataResponse mergeData(@RequestPayload MergeDataRequest request) {

		return mergeService.mergeData(request.getRtfBinaryData(), request.getXmlBinaryData());
	}

	@PayloadRoot(localPart = MERGE_DATA_AND_EXPORT_MESSAGE, namespace = MESSAGES_NAMESPACE)
	@ResponsePayload
	public MergeDataAndConvertResponse mergeDataAndExport(@RequestPayload MergeDataAndConvertRequest request) {

		String conversionType = request.getConversionType();
		this.conversionTypeEnum = Utils.getDescrizioneConversionsSupportedEnum(conversionType);
		return mergeService.mergeDataAndExport(request.getRtfBinaryData(), request.getXmlBinaryData(),
				this.conversionTypeEnum);
	}

	@PayloadRoot(localPart = MERGE_AND_CONVERT_MESSAGE, namespace = MESSAGES_NAMESPACE)
	@ResponsePayload
	public MergeAndConvertResponse mergeAndConvert(@RequestPayload MergeAndConvertRequest request) {

		ConversionsSupportedEnum conversionType = null;
		try {
			conversionType = ConversionsSupportedEnum.valueOf(request.getConversionType());
		} catch (Exception e) {
			throw new RuntimeException("Tipo di conversione non riconosciuta: " + request.getConversionType());
		}
		FileInTypesEnum dataType = null;
		try {
			dataType = FileInTypesEnum.valueOf(request.getDataType());
		} catch (Exception e) {
			throw new RuntimeException("Tipo di file non riconosciuto: " + request.getDataType());
		}
		ModelTypesEnum modelType = null;
		if (StringUtils.isNotBlank(request.getModelType())) {
			try {
				modelType = ModelTypesEnum.valueOf(request.getModelType());
			} catch (Exception e) {
				throw new RuntimeException("Tipo di file del modello non riconosciuto: " + request.getModelType());
			}
		}
		return mergeService.mergeAndConvert(request.getData(), dataType, request.getModel(), modelType, conversionType);
	}
}
