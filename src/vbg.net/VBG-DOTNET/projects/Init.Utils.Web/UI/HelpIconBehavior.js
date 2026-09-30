function HelpIconBehavior(control, helpControl) {
    'use strict';
    
	this.control = control;
	this.helpControl = helpControl;
    
    this.Initialize = function () {
		
        var container;
        
        if (!this.GetHelpControl()) {
            return;
        }
	
        container = document.getElementById("HelpContainer");
		container.appendChild(this.GetHelpControl());
		
		this.GetControl().helpControl = this.GetHelpControl();
		this.GetControl().onmouseover = function (ev) {
			
            var el,
                elWidth,
                scrollTop,
                scrollLeft,
                abstractEvent,
                yPos,
                xPos;
            
			el = this.helpControl;
			el.style.display = '';
            el.style.position = 'absolute';
			
			elWidth = this.helpControl.offsetWidth;

			scrollTop = 0;
            scrollLeft = 0;

			scrollTop  = document.documentElement.scrollTop || document.body.scrollTop;
			scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
			
			abstractEvent = window.event || ev;
			
			yPos = abstractEvent.clientY + scrollTop;
			xPos = abstractEvent.clientX + scrollLeft;
			
			if ((xPos + elWidth) > document.body.offsetWidth) {
                xPos = xPos - elWidth;
            }

			el.style.top = yPos + 'px';
            el.style.left = xPos + 'px';
		};
        
		this.GetControl().onmouseout = function (ev) {
			var el = this.helpControl;
			el.style.display = 'none';
		};
	};
    
	this.GetControl = function () {
		return this.control;
	};
    
	this.GetHelpControl = function () {
		return this.helpControl;
	};
}