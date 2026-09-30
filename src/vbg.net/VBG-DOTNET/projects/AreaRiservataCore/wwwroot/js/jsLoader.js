let loadedScripts = [];

function loadJs(sourceUrl) {
	if (sourceUrl.Length == 0) {
		console.error("Invalid source URL");
		return false;
	}

	if (loadedScripts.indexOf(sourceUrl) > -1)
		return true;
	

	let tag = document.createElement('script');
	tag.src = sourceUrl;
	tag.type = "text/javascript";

	tag.onload = function () {
		console.log("Script loaded successfully");
	}

	tag.onerror = function () {
		console.error("Failed to load script " + sourceUrl);
	}

	document.body.appendChild(tag);
	loadedScripts.push(sourceUrl);

	return true;
}