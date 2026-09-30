class _avviaPagamento {
    avviaPagamento(data) {
        console.log(data);
        const method = data.method;
        const action = data.action;
        const params = data.parameters;

        const form = document.createElement('form');
        form.action = action;
        form.method = method;

        for (var p of params) {
            const el = document.createElement('input');
            el.type = 'hidden';
            el.name = p.key;
            el.value = p.value;

            form.appendChild(el);
        }

        document.body.appendChild(form);
        form.submit();
    }
}

window.AvviaPagamento = new _avviaPagamento();