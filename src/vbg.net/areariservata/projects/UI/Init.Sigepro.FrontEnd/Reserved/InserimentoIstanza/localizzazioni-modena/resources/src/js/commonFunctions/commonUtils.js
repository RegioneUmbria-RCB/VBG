/**
 * questo file contine delle funzioni di utilità di base per la manipolazione di stringhe, numeri ecc..
 */

var commonUtils =
{
	common:
	{
		isUndefinedVar: function (param) {
			if (typeof param === 'undefined' || param == null) {
				return true;
			}
			return false;
		},
		isArray: function (a) {
			return (!!a) && (a.constructor === Array);
		},
		isObject: function (a) {
			return (!!a) && (a.constructor === Object);
		},
		isString: function (a) {
			//modo classico
			//typeof param == 'string'
			return (!!a) && (a.constructor === String);
		},
		isNumber: function (a) {
			return (!!a) && (a.constructor === Number);
		},
		/**
		 * return 0   : i due item a confronto solo uguali
		 * return < 0 : il primo e' minore del secondo
		 * return > 0 : il secondo e' maggiore del primo
		 * @param {string|integer} currentItem 
		 * @param {string|integer} nextItem 
		 */
		compareIntegersAndLetters: function (currentItem, nextItem) {

			var compareResult = 0;
			if (currentItem == undefined || nextItem == undefined) {
				//COMBINAZIONI IN CUI ALMENO UNO DEI DUE ITEM E' NON DEFINITO
				//===========================================================
				if (currentItem == undefined && nextItem == undefined) {
					//entrambi gli elementi non sono definiti quindi sono uguali
					compareResult = 0;
				}
				else if (currentItem != undefined && nextItem == undefined) {
					//solo il primo elemento e' definito, considerato maggiore 
					//il primo rispetto al secondo
					compareResult = 1;
				}
				else if (currentItem == undefined && nextItem != undefined) {
					//solo il secondo elemento e' definito, considerato maggiore
					//il secondo rispetto al primo
					compareResult = -1;
				}
			}
			else {
				//COMBINAZIONI IN CUI ENTRAMBI GLI ITEM SONO DEFINITI
				//====================================================
				var currentItemAsNumber = parseInt(currentItem);
				var nextItemAsNumber = parseInt(nextItem);

				if (isNaN(currentItemAsNumber) && isNaN(nextItemAsNumber)) {
					//current e next entrambi alfabetici. effettuo ordinamento alfabetico
					//compareResult = currentItem.toLowerCase() - nextItem.toLowerCase();
					compareResult = currentItem.localeCompare(nextItem);
				}
				else if (isNaN(currentItemAsNumber) && !isNaN(nextItemAsNumber)) {
					//current alfabetico, next numerico.
					//current (alfabetico) considerato minore di next (numerico)
					compareResult = -1;
				}
				else if (!isNaN(currentItemAsNumber) && isNaN(nextItemAsNumber)) {
					//current numerico, next alfabetico.
					//current (numerico) considerato maggiore di next (alfabetico)
					compareResult = 1;
				}
				else if (!isNaN(currentItemAsNumber) && !isNaN(nextItemAsNumber)) {
					//entrambi sono numerici, effettuare ordinamento aritmetico
					compareResult = currentItemAsNumber - nextItemAsNumber;
				}
			}

			return compareResult;
		}
	},

	array:
	{
		remove_duplicates: function (array_input) {
			var seen = {};
			var array_output = [];
			var len = array_input.length;
			var j = 0;
			for (var i = 0; i < len; i++) {
				var item = array_input[i];
				if (seen[item] !== 1) {
					seen[item] = 1;
					array_output[j++] = item;
				}
			}
			return array_output;
		}
	},

	string:
	{
		/**
		 * verifica se un parametro stringa non e' definito o e' vuoto
		 * @param stringValue
		 * @returns {Boolean}
		 */
		isEmptyOrNull: function (param) {
			if (commonUtils.common.isUndefinedVar(param)) {
				return true;
			}
			else if (commonUtils.common.isString(param) && param.trim().length < 1) {
				return true;
			}
			return false;
		},

		/**
		 * Effettua il trim di una stringa
		 *
		 * @param stringa
		 * @returns
		 */
		trim: function (stringa) {
			while (stringa.substring(0, 1) == ' ') {
				stringa = stringa.substring(1, stringa.length);
			}
			while (stringa.substring(stringa.length - 1, stringa.length) == ' ') {
				stringa = stringa.substring(0, stringa.length - 1);
			}
			return stringa;
		},
		/**
		 * inserisce la stringa in una maschera di padding
		 * es string 1A - pad 00000 - risultato - 0001A
		 *
		 * @param string
		 * @param pad
		 * @returns
		 */
		pad_string: function (string, pad) {
			if (commonUtils.common.isUndefinedVar(string))
				string = "";

			if (commonUtils.common.isUndefinedVar(pad))
				return null;

			var output = (pad + string).slice(-pad.length);
			return output;
		}


	},

	html_list:
	{
		/**
		 * Riordina una lista HTML (un insieme di elementi figli di html_list_object appartenenti allo stesso insieme di tag) 
		 * eseguendo un confronto per il valore di un determinato attributo
		 * @param {*} htmlListObject 
		 * @param {*} listTagType 
		 * @param {*} dataAttribute 
		 */
		sortItemsByAttribute:
			function(htmlListObject, listTagType, attribute) 
			{
				do
				{
					var html_list_elements = wrappers.object.getChildrenByTag(htmlListObject, listTagType);

					var shouldSwitch = false;
					var repeatLoop = false;
					var currentItem, nextItem = undefined;

					for (var counter = 0; counter < (html_list_elements.length - 1); counter++)
					{
						currentItem =	html_list_elements[counter];
						nextItem	=	html_list_elements[counter + 1];

						var currentItemAttributeValue	=	currentItem.getAttribute(attribute);
						var nextItemAttributeValue		=	nextItem.getAttribute(attribute);

						var esitoConfronto = commonUtils.common.compareIntegersAndLetters(currentItemAttributeValue, nextItemAttributeValue);
						if(esitoConfronto > 0)
						{
							//significa che primo elemento maggiore del secondo, quindi deve scendere
							shouldSwitch = true;
							break;
						}
					}

					if(shouldSwitch && currentItem != undefined && nextItem != undefined) 
					{
						//se lo switch e' stato marcato per esecuzione, lo eseguo e lo marco come eseguito
						currentItem.parentNode.insertBefore(nextItem, currentItem);
						repeatLoop = true;
					}
				}while(repeatLoop);
			}
	}

};

/*
===========================================================
UTILIZZABILE CON SINTASSI ES6 - quando superato internet explorer 11
module.exports =  utils;
===========================================================
*/
