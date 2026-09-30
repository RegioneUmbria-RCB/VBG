/*global $, jQuery, Sys*/
function AjaxCall(path, method, async, referenceTracker) {
    'use strict';
    
    this.path = path;
    this.method = method;
    this.params = null;
    this.successCallback = null;
    this.errors = [];
    this.async = async;
    this.referenceTracker = referenceTracker;

    this.withArguments = function (params) {
        this.params = params;
        return this;
    };

    this.withSuccessCallback = function (successCallback) {
        this.successCallback = successCallback;
        return this;
    };

    this.execute = function () {
        var self = this,
            args;

        args = {
            url: this.path + '/' + this.method,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            async: self.async,
            cache: false,
            type: 'POST',
            success: function (res) {
                if (self.successCallback) {
                    self.successCallback(res);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                self.jqWebRequestFailed(jqXHR, textStatus, errorThrown);
            }
        };

        if (this.params !== null) {
            args.data = JSON.stringify(this.params);
        }

        this.referenceTracker.pendingCalls += 1;

        $.ajax(args)
            .done(function () {
                self.referenceTracker.pendingCalls -= 1;
            });

        return this;
    };

    this.jqWebRequestFailed = function (jqXHR, textStatus, errorThrown) {
        this.referenceTracker.pendingCalls -= 1;

        this.errors.push({
            jqXHR: jqXHR,
            textStatus: textStatus,
            errorThrown: errorThrown
        });
    };
}


function AjaxCallManager(path) {
    'use strict';
    
    this.path = path;
    this.async = true;
    this.pendingCalls = 0;

    this.createCall = function (method) {
        return new AjaxCall(this.path, method, this.async, this);
    };

    this.forceSync = function () { this.async = false; };
    this.endForceSync = function () { this.async = true; };
    this.getPendingCallsCount = function () { return this.pendingCalls; };
}