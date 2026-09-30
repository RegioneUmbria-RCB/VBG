(() => {
    class Validator {

        constructor(formGroup) {
            this.isValid = true;
            this.campo = formGroup.querySelector('.control-to-validate');
            this.campoErrore = formGroup.querySelector(".validation-feedback");
            this.campoErrore.style.display = 'none';
            this.onPostValidate = null;

            this.campo.addEventListener('blur', () => {
                // this.onPreValidation();
                this.doValidation();
            });
        }

        get valoreCampo() { return this.campo.value || ''; }
        set valoreCampo(value) { this.campo.value = value; }

        doValidationInternal() {
            return true;
        }

        doValidation() {
            this.isValid = this.doValidationInternal();

            this.campoErrore.style.display = this.isValid ? 'none' : 'block';
            this.campo.classList.toggle('input-error', !this.isValid);

            if (this.onPostValidate) {
                this.onPostValidate();
            }
        }
        
        reset() {
            this.campoErrore.style.display = 'none';
            this.campo.classList.remove('input-error');
        }
    }


    class RequiredFieldValidator extends Validator {

        constructor(formGroup) {
            super(formGroup);
        }

        doValidationInternal() {
            return this.valoreCampo !== '';
        }
    }

    class ValidationGroup {

        constructor(validatori, bottoniSalvataggio) {
            this.validatori = validatori;
            this.validatori.forEach((validatore) => validatore.onPostValidate = () => this.onPostValidate());
            this.bottoniSalvataggio = Array.isArray(bottoniSalvataggio)? bottoniSalvataggio : Array.prototype.slice.call(bottoniSalvataggio);
            this.isValid = true;

            this.bottoniSalvataggio.forEach((btn) => {
                btn.addEventListener('click', (e) => {
                    this.triggerValidation();

                    if (!this.isValid) {
                        e.preventDefault();
                        return false;
                    }

                    return true;
                })
            });
        }

        triggerValidation() {
            this.validatori.forEach((validatore) => validatore.doValidation());
        }

        onPostValidate() {

            this.isValid = this.validatori.reduce((result, validatore) => result && validatore.isValid, true);

            this.bottoniSalvataggio.forEach((btn) => {
                btn.toggleAttribute('disabled', !this.isValid);
                if (btn.hasAttribute('disabled')) {
                    btn.setAttribute('disabled', 'disabled');
                }
            })
        }
        
        resetValidators(){
            this.validatori.forEach((validatore) => validatore.reset());
            
            this.bottoniSalvataggio.forEach((btn) => {
                btn.toggleAttribute('disabled', false);
            });
        }
    }


    window.vbg = {
        ...window.vbg || {},

        validators: {
            Validator: Validator,
            RequiredFieldValidator: RequiredFieldValidator,
            ValidationGroup: ValidationGroup
        }
    };

})();