package it.paevolution.fileconverter2.service;

import it.gruppoinit.fileconverter.MergeAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataResponse;
import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;
import it.paevolution.fileconverter2.service.BaseService.ModelTypesEnum;

public interface MergeService {

    public MergeDataResponse mergeData(byte[] rtfBinaryData, byte[] xmlBinaryData);

    public MergeDataAndConvertResponse mergeDataAndExport(byte[] rtfBinaryData, byte[] xmlBinaryData, ConversionsSupportedEnum conversionType);

    public MergeAndConvertResponse mergeAndConvert(byte[] data, FileInTypesEnum dataType, byte[] model, ModelTypesEnum modelType,
	    ConversionsSupportedEnum conversionType);
}
