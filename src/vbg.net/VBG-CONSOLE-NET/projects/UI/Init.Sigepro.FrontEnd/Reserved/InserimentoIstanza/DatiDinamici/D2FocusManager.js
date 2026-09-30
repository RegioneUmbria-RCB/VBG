/*global $, jQuery, Sys*/
function D2FocusManager() {
    'use strict';
    
    this.lastFocusedControlId = "";

    this.init = function () {
        if (typeof (window.addEventListener) !== "undefined") {
            window.addEventListener("focus", function (e) {
                document.activeElement = e.originalTarget;
            }, true);
        }
    };

    this.saveFocus = function () {
        this.lastFocusedControlId = typeof (document.activeElement) === "undefined" ? "" : document.activeElement.id;
    };

    this.restoreFocus = function () {
        if (typeof (this.lastFocusedControlId) !== "undefined" && this.lastFocusedControlId !== "") {
            var newFocused = $('#' + this.lastFocusedControlId)[0];
            if (newFocused) {
                this.focusControl(newFocused);
            }
        }
    };

    this.focusControl = function (targetControl) {
        var oldContentEditableSetting,
            focusTarget;
        
        try {

            if (Sys.Browser.agent === Sys.Browser.InternetExplorer) {
                
                focusTarget = targetControl;
                
                if (focusTarget && (typeof (focusTarget.contentEditable) !== "undefined")) {
                    oldContentEditableSetting = focusTarget.contentEditable;
                    focusTarget.contentEditable = false;
                } else {
                    focusTarget = null;
                }
                targetControl.focus();

                if (focusTarget) {
                    focusTarget.contentEditable = oldContentEditableSetting;
                }
            } else {
                targetControl.focus();
            }
        } catch (ex) {
        }
    };

    this.init();
}