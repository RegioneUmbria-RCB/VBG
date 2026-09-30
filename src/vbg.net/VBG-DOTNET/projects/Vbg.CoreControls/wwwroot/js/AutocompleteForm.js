window.AutocompleteForm = (function () {

    function calculateControlHeight(_input) {
        let height = 0;
        const validation = _input.parentNode.parentNode.getElementsByClassName("form-control-validation");

        if (validation.length > 0)
            height -= validation[0].offsetHeight;

        return height;
    }

    return {
        init: function (inputElement, popupElement) {

            const _input = document.getElementById(inputElement.id);
            const _popup = document.getElementById(popupElement.id);

            if (!_input || !_popup)
                return;

            _input.addEventListener('focus', () => {
                _popup.classList.remove("hidden");
            });

            window.addEventListener('mousedown', (e) => {

                if (!_input.contains(e.target) && _input != e.target && !_popup.contains(e.target) && _popup != e.target) {
                    _popup.classList.add("hidden");
                }
                else {
                    _popup.classList.remove("hidden");
                }
            });

            window.addEventListener('mouseup', (e) => {

                if (_popup.contains(e.target) && _popup == e.target) {
                    _popup.classList.add("hidden");
                }
            });

            window.onscroll = function () {
                _popup.classList.add("hidden");
            };
        },

        setPopupPosition: function (inputElement, popupElement) {
            if (inputElement == null || popupElement == null)
                return;

            const _input = document.getElementById(inputElement.id);
            const _popup = document.getElementById(popupElement.id);

            if (!_input || !_popup)
                return;

            const autocomplete = _popup.getElementsByClassName("ui-autocomplete");

            if (autocomplete.length > 0)
                autocomplete[0].style.top = calculateControlHeight(_input) + 'px';
        }
    }
    
})();