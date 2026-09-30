<%@ include file="../includes/taglibs.jsp" %>
    <script type="text/javascript">
        vbg.ready(() => {
            const formInserimento = document.getElementById("popup");
            const bottoneNuovo = document.querySelector("#bottoneNuovo");
            const bottoneInserisci = formInserimento.querySelector("#bottone_inserisci");
            const bottoneSalva = formInserimento.querySelector("#bottone_salva");
            const bottoneChiudi = formInserimento.querySelector("#bottone_chiudi");
            const divBottoni = document.querySelector(".form-button");

            formInserimento.style.display = "none";

            /* apre il form di inserimento/modifica */
            bottoneNuovo.addEventListener("click", (e) => {
                console.log("Nuovo");
                mostraForm(true);
            });

            /* chiude il form di inserimento/modifica */
            bottoneChiudi.addEventListener("click", (e) => {
                formInserimento.style.display = "none";
                divBottoni.style.display = "inline-block";
            });

            document.querySelectorAll(".riga-esportazione").forEach((riga) => {
                let idriga = riga.dataset.idRiga;
                let idcomune = riga.querySelector(".riga-esportazione>td").dataset.idComune;

                let bottoneRettifica = riga.querySelector("#modifica");
                let bottoneElimina = riga.querySelector("#elimina");

                bottoneRettifica.addEventListener("click", (e) => {
                    console.log("Modifica riga:", idriga);
                    modificaRiga(idriga);
                });

                bottoneElimina.addEventListener("click", (e) => {
                    console.log("Elimina riga:", idriga);
                    eliminaRiga(idriga, idcomune);
                });
            });

            function modificaRiga(idriga) {
                let riga = document.querySelector("#riga-esportazione" + idriga),
                    idcomune = riga.querySelector(".riga_idcomune").innerHTML,
                    descrizione = riga.querySelector(".riga_descrizione").innerHTML,
                    trasformazione = riga.querySelector(".riga_trasformazione").innerHTML,
                    contesto = riga.querySelector("#riga_tipicontesto_hidden").value,
                    software = riga.querySelector(".riga_software").innerHTML,
                    abilitato = riga.querySelector("#flagabilitato_id").checked;

                mostraForm(false);

                document.getElementById("in_idcomune").value = idcomune;
                document.getElementById("in_descrizione").value = descrizione;
                document.getElementById("in_trasformazione").value = trasformazione;
                document.getElementById("in_contesti").value = contesto;
                document.getElementById("in_software").value = software;
                document.getElementById("in_flgabilitata").checked = abilitato;
                document.getElementById("id_hidden").value = idriga;
            }
            if (bottoneSalva) {
                bottoneSalva.addEventListener("click", async(e) => {
                    e.preventDefault();

                    const {
                        idcomune,
                        descrizione,
                        trasformazione,
                        contesto,
                        software,
                        abilitato,
                    } = PopolaCampi();
                    const codice = document.querySelector("#id_hidden").value;

                    const formData = new FormData();

                    formData.append("idcomune", idcomune);
                    formData.append("descrizione", descrizione);
                    formData.append("trasformazione", trasformazione);
                    formData.append("codiceTipoContesto", contesto);
                    formData.append("idsoftware", software);
                    formData.append("flagAbilitata", abilitato);
                    formData.append("codice", codice);

                    vbg.mostraModalCaricamento();

                    const url = "../esportazioni/modifica.htm";

                    const result = await fetch(url, {
                        method: "POST",
                        body: formData,
                    });

                    const resultText = await result.text();

                    console.log("rt: " + resultText);

                    if (resultText === "OK") {
                        // Ricaricare la tabella
                        location.reload();

                        // Nascondere il popup
                        formInserimento.hide();
                    }
                });
            }

            bottoneInserisci.addEventListener("click", async(e) => {
                e.preventDefault();

                const {
                    idcomune,
                    descrizione,
                    trasformazione,
                    contesto,
                    software,
                    abilitato
                } =
                PopolaCampi();

                const formData = new FormData();

                formData.append("idcomune", idcomune);
                formData.append("descrizione", descrizione);
                formData.append("trasformazione", trasformazione);
                formData.append("codiceTipoContesto", contesto);
                formData.append("idsoftware", software);
                formData.append("flagAbilitata", abilitato);

                console.log(formData);

                vbg.mostraModalCaricamento();

                const url = "../esportazioni/inserisci.htm";

                const result = await fetch(url, {
                    method: "POST",
                    body: formData,
                });

                const resultText = await result.text();

                console.log("rt: " + resultText);

                if (resultText === "OK") {
                    // Ricaricare la tabella
                    location.reload();

                    // Nascondere il popup
                    formInserimento.hide();
                }
            });

           async function eliminaRiga(idriga, idcomune) {

                if (confirm('<fmt:message key="javascript.confirm.delete" />')) {

                    vbg.mostraModalCaricamento();
                    
                    
                    const formData = new FormData();
                    
                    formData.append("idriga", idriga);
                    formData.append("idcomune", idcomune);
                    
                    const url = "../esportazioni/elimina.htm";
                    
                    const result = await fetch (url, {
                    	method : "POST",
                    	body : formData,
                    });
                    
                    const resultText = await result.text();

                    console.log("rt: " + resultText);

                    if (resultText === "OK") {
                        // Ricaricare la tabella
                        location.reload();

                        // Nascondere il popup
                        formInserimento.hide();
                    }
                   
                }
            }

            function mostraForm(isInserimento) {
                formInserimento.style.display = "block";

                if (isInserimento) {
                    document.querySelector(".intestazione").innerHTML =
                        "<h3>Inserimento</h3>";
                    bottoneInserisci.style.display = "inline";
                    bottoneSalva.style.display = "none";
                    document.getElementById("in_idcomune").disabled = false;
                    svuotaCampi();
                } else {
                    document.querySelector(".intestazione").innerHTML = "<h3>Modifica</h3>";
                    bottoneInserisci.style.display = "none";
                    bottoneSalva.style.display = "inline";
                    document.getElementById("in_idcomune").disabled = true;
                }
                divBottoni.style.display = "none";
            }

            function svuotaCampi() {
                document.getElementById("in_idcomune").value = "";
                document.getElementById("in_descrizione").value = "";
                document.getElementById("in_trasformazione").value = "";
                document.getElementById("in_contesti").value = "";
                document.getElementById("in_software").value = "";
                document.getElementById("in_flgabilitata").checked = false;
                document.getElementById("id_hidden").value = "";
            }

            function PopolaCampi() {
                const idcomune = document.getElementById("in_idcomune").value;
                const descrizione = document.getElementById("in_descrizione").value;
                const trasformazione = document.getElementById("in_trasformazione").value;
                const contesto = document.getElementById("in_contesti").value;
                const software = document.getElementById("in_software").value;
                const abilitato = document.getElementById("in_flgabilitata").checked;
                return {
                    idcomune,
                    descrizione,
                    trasformazione,
                    contesto,
                    software,
                    abilitato
                };
            }
        });
    </script>