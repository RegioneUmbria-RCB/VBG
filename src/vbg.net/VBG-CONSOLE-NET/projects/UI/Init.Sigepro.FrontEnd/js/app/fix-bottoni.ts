import * as $ from "jquery";

export function applyFix() {
    $(function () {
        $('.bottoni>input[type=submit]').addClass('btn btn-primary');
    });
}