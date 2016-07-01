// JScript File
var g_debug = true;

DebugClass = function () {
    this.m_currBlock = [];
};

DebugClass.prototype = {

	Write : function(stringa )
	{
	
		if (!g_debug) return;

		var el = document.getElementById("debugDiv");
		
		if (!el)
		{
			el = document.createElement("DIV");
			el.id = "debugDiv";
			document.forms[0].appendChild(el);
			el.innerHTML = "<b>DEBUG JAVASCRIPT:</b><br>";
		}
		
		el.innerHTML += "<span style='padding-left:" + this.m_currBlock.length * 10 + "px'>" + stringa + "</span><br>";
		
	},
	BeginBlock: function(nomeBlocco)
	{
		this.Write( "<b>Inizio debug del blocco " + nomeBlocco + "</b>" );
		this.m_currBlock.push( nomeBlocco );
		
	},
	EndBlock: function(stringa)
	{
		var nomeBlocco = this.m_currBlock.pop();
		this.Write( "<b>Fine debug del blocco " + nomeBlocco + "</b>" );
		
	},
	
	GetFunctionName: function(theFunction)
	{
		// mozilla makes it easy. I love mozilla.
		if(theFunction.name)
		{
			return theFunction.name;
		}
		
		// try to parse the function name from the defintion
		var definition = theFunction.toString();
		var name = definition.substring(definition.indexOf('function') + 8,definition.indexOf('('));
		if(name)
			return name;

		// sometimes there won't be a function name 
		// like for dynamic functions
		return "anonymous";
	},
};

var Debug = new DebugClass();


function FixUpdatePanel(){
	var updatePanels = $('div[id*=UpdatePanel] ');

	$.each(updatePanels, function (idx, el) {
		$(el).addClass('UpdatePanel');
	});
}

function FixLabelObbligatorie(){
	$.each($('label'), function (idx, el) {
		var txt = $(el).text();

		if (txt.length > 0 && txt.slice(0, 1) === '*') {
			$(el).addClass('obbligatorio');
		}
	});
}


function PreserveFormLayout()
{
	var fieldsets = document.getElementsByTagName( "fieldset" );
	
	if (!fieldsets || (fieldsets.length === 0)) return;
	
	
	var minWidth = 0;
	
	for( var fsIdx = 0 ; fsIdx < fieldsets.length  ; fsIdx++ )
	{
		var fieldset = fieldsets[fsIdx];
		
		var temp = CalcolaLarghezzaMaxDivFiglio( fieldset.childNodes );
		
		if (temp > minWidth)
			minWidth = temp;
	}
	
	//Debug.Write( "<b>MinWidth</b>:" + minWidth );
	
	for( fsIdx = 0 ; fsIdx < fieldsets.length  ; fsIdx++ )
	{
		fieldsets[fsIdx].style.minWidth = minWidth + "px";
	}
}

function CalcolaLarghezzaMaxDivFiglio( nodiFiglio )
{
	var minWidth = 0;
	
	for( var i = 0 ; i < nodiFiglio.length ; i++ )
	{
		if (nodiFiglio[i].tagName == "DIV")
		{
			var temp = 0;
		
			if ( nodiFiglio[i].id.toUpperCase().indexOf("UPDATEPANEL") > -1 )
			{
				//Debug.Write("Il div è un layoutpanel, passo ai nodi figlio");
				temp = CalcolaLarghezzaMaxDivFiglio( nodiFiglio[i].childNodes );
			}
			else
			{
				temp = CalcolaLarghezza( nodiFiglio[i] );
			}			
			
			//Debug.Write( "<b>DIV" + i + " (" + nodiFiglio[i].className  + ")  Temp</b>:" + temp );
			
			if (temp > minWidth ) minWidth = temp;
		}
	}
	
	return minWidth;
}

/*
function getStyle(el,styleProp)
{
	var x = el;//document.getElementById(el);
	var y;
	if (x.currentStyle)
	{
		//Debug.Write( styleProp );
		y = x.currentStyle[styleProp];
	}
	else if (window.getComputedStyle)
	{
		y = document.defaultView.getComputedStyle(el,null).getPropertyValue(styleProp);
	}
	return y;
}
*/
function CalcolaLarghezza(el)
{
	if(el.tagName == "TABLE") 
	{
		return 0;
	}

	var risultato = 0;
	var childElements = el.childNodes;
	if (!childElements || childElements.length === 0 || !ContainsValidChilds( el ))
	{
		if (el.tagName)
		{
			var jqEl = $(el);
			var larghezza = jqEl.width();
			var rMargin = parseInt( jqEl.css('margin-right') , 10 );// getStyle(el,"marginRight").replace("px","");
			var lMargin = parseInt( jqEl.css('margin-left') , 10 );//getStyle(el,"marginLeft").replace("px","");
			
			if (isNaN(rMargin))
				rMargin = 0;
			if (isNaN(lMargin))
				lMargin = 0;
				
			risultato = larghezza + parseInt(lMargin) + parseInt(rMargin);
		}
	
		if (el.className == "DescrizioneCampo")
			risultato = 0;		
		
		return parseInt(risultato);
	}
	for (var i = 0 ; i < childElements.length ; i++ )
	{
		risultato += CalcolaLarghezza(childElements[i]);
	}
	return risultato;
}

function ContainsValidChilds(el)
{
	for(var i = 0; i < el.childNodes.length ; i++)
		if (el.childNodes[i].nodeType == 1) return true;
	return false;
}

function FixLayout()
{
	FixLabelObbligatorie();
	FixUpdatePanel();
	PreserveFormLayout();
}

function PosizioneAssoluta( el )
{
	var ret = {
        x: 0,
        y: 0
    };
	
	while(el.offsetParent) {
		ret.x += el.offsetLeft;
		ret.y += el.offsetTop;
		
        el = el.offsetParent;
	}
	
	return ret;
}


function confermaEliminazione(){ return confirm('Proseguire con l\'eliminazione?');}