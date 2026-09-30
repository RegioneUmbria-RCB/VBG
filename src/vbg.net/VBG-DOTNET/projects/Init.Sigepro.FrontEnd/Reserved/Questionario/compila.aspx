<%@ Page Title="Compilazione questionario" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="compila.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Questionario.Compila" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content runat="server" ContentPlaceHolderID="headPagina">
    <style>
        .valutazione {
            list-style-type: none;
            cursor: pointer;
            margin: 0;
            padding: 0;
            display: inline-block;
            font-size: 1.5em;
        }

            .valutazione li {
                display: inline-block;
            }

        .help-block.with-errors {
            display: none;
        }

        .has-error .help-block.with-errors {
            display: block;
        }

        .descrizioneStep {
            margin-bottom: 24px;
        }
    </style>

    <script>

        ready(() => {

            let stelleAttive = 0;
            const stelleEl = document.querySelectorAll('.valutazione>li');
            const hidValutazione = document.getElementById('<%=hidValutazione.ClientID%>');
            const valutazioneGroup = document.getElementById('valutazione-group');

            function setStelleAttive(val) {
                hidValutazione.value = val.toString();
                stelleAttive = val;
                valutazioneGroup.classList.remove('has-error');
                valutazioneGroup.classList.add('has-success');
            }

            function mostraStelle(count) {
                for (let i = 0; i < stelleEl.length; i++) {
                    const icona = stelleEl[i].querySelector('i');
                    icona.className = `fa fa-star${(i < count) ? '' : '-o'}`;
                }
            }

            document.querySelector('.valutazione').addEventListener('mouseleave', () => {
                mostraStelle(stelleAttive);
            });

            stelleEl.forEach(it => {
                it.addEventListener('mouseover', () => {
                    const val = +it.dataset.stelle;
                    mostraStelle(val);
                });

                it.addEventListener('click', () => {
                    setStelleAttive( +it.dataset.stelle);
                });
            });

            document.getElementById('<%=cmdInviaValutazione.ClientID%>').addEventListener('click', (e) => {
                if (hidValutazione.value === '') {
                    valutazioneGroup.classList.add('has-error');
                    e.preventDefault();
                }
            });
        });

    </script>
</asp:Content>

<asp:Content runat="server" ContentPlaceHolderID="ContentPlaceHolder1">

    <div class="descrizioneStep">
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="questionario.titolo-pagina">
            Come valuta la qualità e il livello di servizio offerto dal portale riguardo la presentazione di pratiche telematiche?
        </ar:RisorsaTestualeLabel>
    </div>

    <div class="form-group" id="valutazione-group">
        <label class="control-label">Valutazione<sup>*</sup></label>
        <div class="form-control">
            <asp:HiddenField runat="server" ID="hidValutazione" />
            <ul class="valutazione">
                <li data-stelle="1"><i class="fa fa-star-o"></i></li>
                <li data-stelle="2"><i class="fa fa-star-o"></i></li>
                <li data-stelle="3"><i class="fa fa-star-o"></i></li>
                <li data-stelle="4"><i class="fa fa-star-o"></i></li>
                <li data-stelle="5"><i class="fa fa-star-o"></i></li>
            </ul>
        </div>
        <div class="help-block with-errors">Campo obbligatorio</div>
    </div>

    <ar:TextBox runat="server" ID="txtNote" TextMode="MultiLine" Label="Suggerimenti" InnerAttributes="data-max-caratteri=4000" InnerCssClass="limita-lunghezza" Rows="4"/>


    <asp:Button runat="server" ID="cmdInviaValutazione" CssClass="btn btn-primary vbg-show-spinner-if-valid" Text="Invia valutazione" OnClick="cmdInviaValutazione_Click" />
    <asp:Button runat="server" ID="cmdChiudi" CssClass="btn btn-default vbg-show-spinner" Text="Torna alla pratica senza lasciare una valutazione" OnClick="cmdChiudi_Click" />
</asp:Content>
