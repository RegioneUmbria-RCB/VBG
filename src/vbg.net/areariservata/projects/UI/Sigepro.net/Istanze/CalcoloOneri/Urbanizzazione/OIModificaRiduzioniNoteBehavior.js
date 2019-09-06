
OIModificaRiduzioniNote = function( id , activateBtn , associatedControl )
{
	this._id = id;
	this._control = document.getElementById(id);
	this._activateBtn = document.getElementById(activateBtn);
	this._associatedControl = document.getElementById(associatedControl);
}

OIModificaRiduzioniNote.prototype = {
	getControl : function(){return this._control;},
	getId : function(){ return this._id;},
	getActivateBtn : function(){ return this._activateBtn;},
	getAssociatedControl : function(){return this._associatedControl;},
	Initialize : function()
	{
		this.getControl().style.visibility = "hidden";
		this.getControl().style.position = "absolute";
		
		var container = document.getElementById("HelpContainer");
		container.appendChild(this.getControl());

		if (!this.getActivateBtn()) return;
		
		this.getActivateBtn().style.visibility = this.getAssociatedControl().checked ? "visible" : "hidden";
		
		this.getActivateBtn().ReferenceCtrl = this.getAssociatedControl().id;
		this.getActivateBtn().ShowHideCtrlId = this.getId();
		this.getActivateBtn().AbsolutePos =  function( el )
												{
													var ret = new Object();
													ret["x"] = 0;
													ret["y"] = 0;
													
													if (el.offsetParent) {
														do {
															ret.x += el.offsetLeft;
															ret.y += el.offsetTop;
														} while (el = el.offsetParent);
													}
													
													return ret;
												};
		this.getActivateBtn().onclick = function()
										{
											var ctrl = document.getElementById( this.ShowHideCtrlId );
											
											var pos = this.AbsolutePos( this );
											
											ctrl.style.pixelTop = pos.y + this.offsetHeight;
											ctrl.style.pixelLeft = pos.x - ctrl.offsetWidth + this.offsetWidth;
											
											if (ctrl.style.visibility == "visible")
												ctrl.style.visibility = "hidden";
											else
												ctrl.style.visibility = "visible";
												
											return false;
										};
		FixLayout();
	}

}

OIModificaRiduzioniNoteMgr = function()
{
	this._controls = new Array();
}

OIModificaRiduzioniNoteMgr.prototype = {
	AddControl: function ( ctrlId , activateBtn , associatedControl )
	{
		this.getControls()[ ctrlId ] = new OIModificaRiduzioniNote( ctrlId , activateBtn , associatedControl );
		this.getControls()[ ctrlId ].Initialize();
	},
	Update : function()
	{
		for( var ctrlId in this.getControls() )
			this.getControls()[ ctrlId ].Initialize();
	},
	getControls : function(){ return this._controls; }
}

var g_oIModificaRiduzioniNoteMgr = new OIModificaRiduzioniNoteMgr();