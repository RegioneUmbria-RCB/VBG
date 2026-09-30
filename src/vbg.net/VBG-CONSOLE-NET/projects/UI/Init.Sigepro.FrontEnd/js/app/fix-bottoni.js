define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    exports.applyFix = applyFix;
    function applyFix() {
        $(function () {
            $('.bottoni>input[type=submit]').addClass('btn btn-primary');
        });
    }
});
//# sourceMappingURL=fix-bottoni.js.map