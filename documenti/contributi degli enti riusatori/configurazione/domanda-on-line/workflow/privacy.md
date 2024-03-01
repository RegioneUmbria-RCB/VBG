# Step gestione privacy

## Parametri

### MessaggioErrore (string)

Imposta il messaggio di errore da mostrare nel caso in cui l'utente prosegua senza aver spuntato l'accettazione della privacy

Valore default: "Per proseguire è necessario leggere ed accettare le condizioni riportate"

### TestoPrivacy (string)

Testo privacy da accettare, può contenere il segnaposto "{0}" in cui verrà riportato il nome del comune presso cui si sta presentando la domanda

### TestoAccettazionePrivacy (string)

Testo da mostrare a fianco della checkbox di accettazione per indicare l'accettazione.

Valore default: "Accetto"

### TestoPrivacyMarkdown (boolean)

Se impostato a "true" interpreta il testo configurato in **TestoPrivacy** come stringa markdown e lo converte in html

Valore default: "false"

### MostraCheckAccettazione (boolean)

Se impostato a false la checkbox di accettazione non viene mostrata e l'informativa viene accettata implicitamente

Valore default: "true"