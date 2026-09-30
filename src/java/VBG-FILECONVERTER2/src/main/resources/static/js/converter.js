function dismissError() {
	
	$('#errorMessage').empty();
}

function showError(errorMessage) {

	if (errorMessage) {
		var $alert = $('<div>', {'class': 'alert alert-danger alert-dismissable fade in'});
		$alert.append('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>')
		$alert.append('<strong>Error!</strong>&nbsp;&nbsp;');
		$alert.append('<span>' + errorMessage +'</span>');
		
		// Replace current HTML of the error message div
		$('#errorMessage').html($alert);
	} else {
		// Remove older message from the error message div
		dismissError();
	}
}

function disableElement(eltId) {

	var $elt = $('#' + eltId);
	if(!$elt.attr('disabled')) {
		$elt.attr('disabled', 'disabled');
	}
}

function onLoad() {
	
	var ext = 'html';
	// Retrieve the input family for this extension
	var family = importFormatTable[ext];
	
	
	
	// Get the supported output formats for the input format family
	var formats = exportFormatTable[family];
	
	// Populate the drop down list of supported output formats
	var $outputFormat = $("#outputFormat0");
	$.each(formats, function() {
		if (this.value != ext) {
			$outputFormat.append(this);
		}
	});
	
	$("#outputFormat0").removeAttr('disabled');
	$("#goButton0").removeAttr('disabled');
}

function onInputFileChange() {
	
	// Remove any previous error message
	dismissError();
	
	// Obtain a jQuery object that represents the input file
	var $inputFile = $('#inputFile');
	
	// Extract the filename from the jQuery object
	var filename = $inputFile.val().split('\\').pop();

	// Update the read-only input field showing the selected file
	$('#inputFileText').val(filename);

	// Search for an extension in the filename
	// See https://stackoverflow.com/a/680982/4336562
	var re = /(?:\.([^.]+))?$/;
	var ext = re.exec(filename)[1];
	if (ext == undefined) {
		disableElement('outputFormat');
		disableElement('goButton');
		showError('No extension found in the source file name.');
		return false;
	}
	
	// Retrieve the input family for this extension
	var family = importFormatTable[ext.toLowerCase()];
	
	// Input format not supported ? Inform the user.
	if (family == undefined) {
		disableElement('outputFormat');
		disableElement('goButton');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}
	
	// Get the supported output formats for the input format family
	var formats = exportFormatTable[family];
	
	// Populate the drop down list of supported output formats
	var $outputFormat = $("#outputFormat");
	$.each(formats, function() {
		if (this.value != ext) {
			$outputFormat.append(this);
		}
	});
	
	$("#outputFormat").removeAttr('disabled');
	$("#goButton").removeAttr('disabled');
}

function onInputFileRTFChange() {
	
	// Remove any previous error message
	dismissError();
	
	// Obtain a jQuery object that represents the input file
	var $inputFile = $('#inputFileRTF');
	
	// Extract the filename from the jQuery object
	var filename = $inputFile.val().split('\\').pop();

	// Update the read-only input field showing the selected file
	$('#inputFileTextRTF').val(filename);

	// Search for an extension in the filename
	// See https://stackoverflow.com/a/680982/4336562
	var re = /(?:\.([^.]+))?$/;
	var ext = re.exec(filename)[1];
	if (ext == undefined) {
		disableElement('goButtonMerge');
		showError('No extension found in the source file name.');
		return false;
	}
	
	// Input format not supported ? Inform the user.
	if ("rtf" != ext.toLowerCase()) {
		disableElement('goButtonMerge');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}

	$("#goButtonMerge").removeAttr('disabled');
}

function onInputFileXMLChange() {
	
	// Remove any previous error message
	dismissError();
	
	// Obtain a jQuery object that represents the input file
	var $inputFile = $('#inputFileXML');
	
	// Extract the filename from the jQuery object
	var filename = $inputFile.val().split('\\').pop();

	// Update the read-only input field showing the selected file
	$('#inputFileTextXML').val(filename);

	// Search for an extension in the filename
	// See https://stackoverflow.com/a/680982/4336562
	var re = /(?:\.([^.]+))?$/;
	var ext = re.exec(filename)[1];
	if (ext == undefined) {
		disableElement('goButtonMerge');
		showError('No extension found in the source file name.');
		return false;
	}
	
	// Input format not supported ? Inform the user.
	if ("xml" != ext.toLowerCase()) {
		disableElement('goButtonMerge');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}

	$("#goButtonMerge").removeAttr('disabled');
}


function onInputFileRTFChange1() {
	
	// Remove any previous error message
	dismissError();
	
	// Obtain a jQuery object that represents the input file
	var $inputFile = $('#inputFileRTF1');
	
	// Extract the filename from the jQuery object
	var filename = $inputFile.val().split('\\').pop();

	// Update the read-only input field showing the selected file
	$('#inputFileTextRTF1').val(filename);

	// Search for an extension in the filename
	// See https://stackoverflow.com/a/680982/4336562
	var re = /(?:\.([^.]+))?$/;
	var ext = re.exec(filename)[1];
	if (ext == undefined) {
		disableElement('outputFormat1');
		disableElement('goButtonMergeExport');
		showError('No extension found in the source file name.');
		return false;
	}
	
	// Retrieve the input family for this extension
	var family = importFormatTable[ext.toLowerCase()];
	
	// Input format not supported ? Inform the user.
	if (family == undefined) {
		disableElement('outputFormat1');
		disableElement('goButtonMergeExport');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}
	
	// Get the supported output formats for the input format family
	var formats = exportFormatTable[family];
	
	// Populate the drop down list of supported output formats
	var $outputFormat = $("#outputFormat1");
	$.each(formats, function() {
		if (this.value != ext) {
			$outputFormat.append(this);
		}
	});
	
	// Input format not supported ? Inform the user.
	if ("rtf" != ext.toLowerCase()) {
		disableElement('outputFormat1');
		disableElement('goButtonMergeEport');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}
	$("#outputFormat1").removeAttr('disabled');
	$("#goButtonMergeExport").removeAttr('disabled');
}

function onInputFileXMLChange1() {
	
	// Remove any previous error message
	dismissError();
	
	// Obtain a jQuery object that represents the input file
	var $inputFile = $('#inputFileXML1');
	
	// Extract the filename from the jQuery object
	var filename = $inputFile.val().split('\\').pop();

	// Update the read-only input field showing the selected file
	$('#inputFileTextXML1').val(filename);

	// Search for an extension in the filename
	// See https://stackoverflow.com/a/680982/4336562
	var re = /(?:\.([^.]+))?$/;
	var ext = re.exec(filename)[1];
	if (ext == undefined) {
		disableElement('outputFormat1');
		disableElement('goButtonMergeExport');
		showError('No extension found in the source file name.');
		return false;
	}
	
	
	// Input format not supported ? Inform the user.
	if ("xml" != ext.toLowerCase()) {
		disableElement('outputFormat1');
		disableElement('goButtonMergeEport');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}
	$("#outputFormat1").removeAttr('disabled');
	$("#goButtonMergeExport").removeAttr('disabled');
}

function onInputFileXMLChange2() {
	
	// Remove any previous error message
	dismissError();
	
	// Obtain a jQuery object that represents the input file
	var $inputFile = $('#inputFileXML2');
	
	// Extract the filename from the jQuery object
	var filename = $inputFile.val().split('\\').pop();

	// Update the read-only input field showing the selected file
	$('#inputFileTextXML2').val(filename);

	// Search for an extension in the filename
	// See https://stackoverflow.com/a/680982/4336562
	var re = /(?:\.([^.]+))?$/;
	var ext = re.exec(filename)[1];
	if (ext == undefined) {
		disableElement('outputFormat2');
		disableElement('goButtonMergeConvert');
		showError('No extension found in the source file name.');
		return false;
	}
	
	// Retrieve the input family for this extension
	var family = importFormatTable['html'];
	
	// Input format not supported ? Inform the user.
	if (family == undefined) {
		disableElement('outputFormat2');
		disableElement('goButtonMergeConvert');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}
	
	// Get the supported output formats for the input format family
	var formats = exportFormatTable[family];
	
	// Populate the drop down list of supported output formats
	var $outputFormat = $("#outputFormat2");
	$.each(formats, function() {
		if (this.value != ext) {
			$outputFormat.append(this);
		}
	});
	
	// Input format not supported ? Inform the user.
	if ("xml" != ext.toLowerCase()) {
		disableElement('outputFormat2');
		disableElement('goButtonMergeConvert');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}
	$("#outputFormat2").removeAttr('disabled');
	$("#goButtonMergeConvert").removeAttr('disabled');
}

function onInputFileXSLChange2() {
	
	// Remove any previous error message
	dismissError();
	
	// Obtain a jQuery object that represents the input file
	var $inputFile = $('#inputFileXSL2');
	
	// Extract the filename from the jQuery object
	var filename = $inputFile.val().split('\\').pop();

	// Update the read-only input field showing the selected file
	$('#inputFileTextXSL2').val(filename);

	// Search for an extension in the filename
	// See https://stackoverflow.com/a/680982/4336562
	var re = /(?:\.([^.]+))?$/;
	var ext = re.exec(filename)[1];
	if (ext == undefined) {
		disableElement('outputFormat2');
		disableElement('goButtonMergeConvert');
		showError('No extension found in the source file name.');
		return false;
	}
	
	
	// Input format not supported ? Inform the user.
	if ("xsl" != ext.toLowerCase()) {
		disableElement('outputFormat2');
		disableElement('goButtonMergeConvert');
		showError('Conversion from extension <b>' + ext + '</b> is not supported.');
		
		return false;
	}
	$("#outputFormat2").removeAttr('disabled');
	$("#goButtonMergeConvert").removeAttr('disabled');
}