/// <reference name="MicrosoftAjax.debug.js" />
/// <reference name="MicrosoftAjaxTimer.debug.js" />
/// <reference name="MicrosoftAjaxWebForms.debug.js" />
/// <reference path="../ExtenderBase/BaseScripts.js" />
/// <reference path="../Common/Common.js" />


Type.registerNamespace('SIGePro.WebControls.Ajax');

SIGePro.WebControls.Ajax.RicerchePopupBehavior = function(element) {
    SIGePro.WebControls.Ajax.RicerchePopupBehavior.initializeBase(this, [element]);
    
    // handlers
    this._keyDownHandler	= null;
    this._cercaClickHandler = null;
    
    // proprietà del controllo
    this.m_popupUrl		= "";
    this.m_descriptionControlID = "";
    this.m_readOnly		= false;
    this.m_popupWidth	= 400;
    this.m_popupHeight	= 400;
    this.m_querystringParameters = null;
    
    // bottone cerca
    this.m_hrefCerca = null;
}

SIGePro.WebControls.Ajax.RicerchePopupBehavior.prototype = {
    initialize: function() {
        /// <summary>
        /// Initializes the autocomplete behavior.
        /// </summary>
        /// <returns />
        SIGePro.WebControls.Ajax.RicerchePopupBehavior.callBaseMethod(this, 'initialize');
        $common.prepareHiddenElementForATDeviceUpdate();
        
        this._keyDownHandler	= Function.createDelegate(this, this._onKeyDown);
        this._cercaClickHandler = Function.createDelegate(this, this._onCercaClick);
        
        var element = this.get_element();
        this.initializeTextBox(element);
        
        var descriptionElement = $get( this.get_descriptionControlID() );
        this.initializeTextBox(descriptionElement);
        
        // Aggiunta del bottone "Cerca"
        if (!this.get_readOnly())
        {
			this.m_hrefCerca = document.createElement('a');
			this.m_hrefCerca.href = "#";
			this.m_hrefCerca.innerHTML = "Cerca&nbsp;&raquo;";
	        
			$addHandler( this.m_hrefCerca, "click", this._cercaClickHandler );
	        
			element.parentNode.insertBefore(this.m_hrefCerca, element.nextSibling);	
        }
        
        // Evita sconvolgimenti al layout del form
        if(typeof(PreserveFormLayout) == 'function')
			PreserveFormLayout();
	},
	
	dispose: function() {
	
		var element = this.get_element();
        if (element) { $removeHandler(element, "keydown", this._keyDownHandler); }
        
        var descriptionElement = $get( this.get_descriptionControlID() );
		if (descriptionElement) { $removeHandler(descriptionElement, "keydown", this._keyDownHandler); }
		
		if(this.m_hrefCerca){ $removeHandler( this.m_hrefCerca, "click", this._cercaClickHandler); }
	},
	
	initializeTextBox: function(element) {
        /// <summary>
        /// Initializes the textbox
        /// </summary>
        /// <param name="element" type="Sys.UI.DomElement" DomElement="true" mayBeNull="false" />
        /// <returns />    
        element.onchangeOld = element.onchange;
        element.onchange = function(){};
        
        $addHandler(element, "keydown", this._keyDownHandler);
    },
	
	_onKeyDown: function(ev) {
	    var k = ev.keyCode ? ev.keyCode : ev.rawEvent.keyCode;
        if (k === Sys.UI.Key.esc) {
			this.setText( null );
        };
        
        if (k === Sys.UI.Key.del) {
			this.setText( null );
        };
        
        if (k === Sys.UI.Key.backspace) {
			this.setText( null );
        };
        
        if (k !== Sys.UI.Key.tab) {	ev.preventDefault(); }
	},
	
	_onCercaClick : function(ev){
		/*Debug.Write( "-->" + this.get_popupUrl() );*/
		var arguments = "";
		var feats = "dialogHeight:" + this.get_popupHeight() + "px;";
		feats	 += "dialogWidth:" + this.get_popupWidth() + "px;";
		
		var querystring = "";
		var querystringParams = this.get_querystringParameters();
		
		for ( var key in querystringParams ) {
			
			if (querystring)
				querystring += "&";
			
			querystring += key + "=" + querystringParams[key];
		}
		
		var url = this.get_popupUrl();
		
		if (querystring.length > 0)
		{
			if ( url.indexOf( "?" ) != -1 )
				url += "&" + querystring;
			else
				url += "?" + querystring;
		}
		
		//Debug.Write( url );
		
		var res = window.showModalDialog( url , arguments , feats );
		
		this.setText( res );
	},
	
	setText : function( item ){
	
		if (item)
		{
			this._setElementValue( item.codice );
			this._setDescriptionElementValue( item.descrizione );
		}
		else
		{
			this._setElementValue( "" );
			this._setDescriptionElementValue( "" );
		}
	},
	
	_setElementValue : function( value ){
		var element	= this.get_element();
		
		this._setControlValue( element , value );
	},

	_setDescriptionElementValue : function( value ){
		var element	= $get( this.get_descriptionControlID() );
		
		this._setControlValue( element , value );
	},
	
	_setControlValue : function( element , value ){
		if (!element) return;		
		
		var control = element.control;
        
        if (control && control.set_text) {
            control.set_text(value);
            
            if (element.onchangeOld)
				element.onchangeOld();
        }
        else {
            element.value = value;
            
            if (element.onchangeOld)
				element.onchangeOld();
        }	
	},
	
	// proprietà popupUrl
	get_popupUrl : function(){ return this.m_popupUrl; },
	set_popupUrl : function( value ){ this.m_popupUrl = value; /*Debug.Write("-->" + value);*/},
	
	// id controllo descrizione
	get_descriptionControlID : function(){ return this.m_descriptionControlID; },
	set_descriptionControlID : function( value ){ this.m_descriptionControlID = value; },
	
	// Readonly
	get_readOnly : function(){ return this.m_readOnly; },
	set_readOnly : function( value ){ this.m_readOnly = value; },
	
	// popup width
	get_popupWidth : function(){ return this.m_popupWidth; },
	set_popupWidth : function( value ){ this.m_popupWidth = value; },
	
	// popup width
	get_popupHeight : function(){ return this.m_popupHeight; },
	set_popupHeight : function( value ){ this.m_popupHeight = value; },
	
	// Parametri in querystring
	get_querystringParameters : function(){ return this.m_querystringParameters; },
	set_querystringParameters : function( value ){ this.m_querystringParameters = Sys.Serialization.JavaScriptSerializer.deserialize('(' + value + ')'); }
}

SIGePro.WebControls.Ajax.RicerchePopupBehavior.registerClass('SIGePro.WebControls.Ajax.RicerchePopupBehavior', Sys.UI.Behavior);