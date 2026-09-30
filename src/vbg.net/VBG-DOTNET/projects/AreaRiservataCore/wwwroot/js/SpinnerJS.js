window.SpinnerJs = (function () {
    var spinnerClassName = "SpinnerJs";

    return {
        setSpinnerClassName: function (className) {
            spinnerClassName = className;
        },

        show: function () {
            var elements = document.getElementsByClassName(spinnerClassName);

            for (var x = 0; x < elements.length; x++)
                elements[x].style.display = 'block';
        },

        hide: function () {
            var elements = document.getElementsByClassName(spinnerClassName);

            for (var x = 0; x < elements.length; x++)
                elements[x].style.display = 'none';
        }
    }
})();