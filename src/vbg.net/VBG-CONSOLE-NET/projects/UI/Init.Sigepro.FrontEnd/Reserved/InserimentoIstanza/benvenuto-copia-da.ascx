<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="benvenuto-copia-da.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.benvenuto_copia_da" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<script type="text/javascript">
    $(function onload() {

        function onCheckboxClick() {
            var el = $(this),
                checked = el.is(":checked");

            console.log(el);

            el.closest("tr").find(".dropdown-tipo-soggetto").toggle(checked);

            updateValidators();
        }

        $(".checkbox-usa-in-domanda input[type=checkbox]").click(onCheckboxClick);


        $(".checkbox-usa-in-domanda input[type=checkbox]").each(function (idx, item) {
            $.proxy(onCheckboxClick, item)();
        });

        updateValidators();
    });
</script>
<h2>Soggetti</h2>
<div>
    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-benvenuto-descrizione-copia-soggetti" ExtraCssClass="alert alert-info">
        La domanda inviata precedentemente contiene le seguenti anagrafiche, tali anagrafiche possono essere copiate nella domanda che si sta presentando in modo da non doverle compilare nuovamente. <br />
        Seleziona le anagrafiche che si intendono riportare nella domanda corrente.
    </ar:RisorsaTestualeLabel>
</div>
<asp:GridView runat="server" ID="gvSoggetti" CssClass="table" GridLines="None"
    DataKeyNames="Id" AutoGenerateColumns="false" OnRowDataBound="gvSoggetti_RowDataBound">
    <Columns>
        <asp:BoundField HeaderText="Id" DataField="Id" />
        <asp:BoundField HeaderText="Nominativo/Ragione sociale" DataField="Nominativo" />
        <asp:BoundField HeaderText="Qualifica originale" DataField="TipoSoggetto" />
        <asp:BoundField HeaderText="Collegato a" DataField="SoggettoCollegato" />
        <asp:TemplateField HeaderText="Usa nella nuova domanda">
            <ItemTemplate>
                <asp:CheckBox runat="server" ID="chkUsaInDomanda" Checked='<%#Eval("UsaNellaNuovaDomanda") %>' CssClass="checkbox-usa-in-domanda" />
            </ItemTemplate>
        </asp:TemplateField>
        <asp:TemplateField HeaderText="Nuova qualifica">
            <ItemTemplate>
                <ar:DropDownList runat="server" ID="ddlTipoSoggetto" CssClass="dropdown-tipo-soggetto" Required="true" DataTextField="Descrizione" DataValueField="Codice"></ar:DropDownList>
            </ItemTemplate>
        </asp:TemplateField>
    </Columns>
</asp:GridView>

<h2>Allegati</h2>
    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-benvenuto-descrizione-copia-allegati" ExtraCssClass="alert alert-info">
        La domanda inviata precedentemente contiene i seguenti allegati, tali allegati possono essere copiati nella domanda che si sta presentando come allegati liberi in modo da non doverli allegare nuovamente. <br />
        Seleziona gli allegati che si intendono riportare nella domanda corrente.
    </ar:RisorsaTestualeLabel>
<asp:GridView runat="server" ID="gvAllegati" CssClass="table" GridLines="None" DataKeyNames="Id" AutoGenerateColumns="false">
    <Columns>
        <asp:BoundField HeaderText="Id" DataField="Id" />
        <asp:BoundField HeaderText="Descrizione" DataField="Descrizione" />
        <asp:BoundField HeaderText="File" DataField="AllegatoDellUtente.NomeFile" />
        <asp:TemplateField HeaderText="Usa nella nuova domanda">
            <ItemTemplate>
                <asp:CheckBox runat="server" ID="chkUsaInDomanda" CssClass="checkbox-usa-in-domanda" Checked="true" />
            </ItemTemplate>
        </asp:TemplateField>
    </Columns>
</asp:GridView>
