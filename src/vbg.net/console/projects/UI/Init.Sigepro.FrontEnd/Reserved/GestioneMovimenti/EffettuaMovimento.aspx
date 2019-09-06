<%@ Page Title="Riepilogo scadenza" ClientIDMode="Static" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="EffettuaMovimento.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.EffettuaMovimento" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <div id="contenuto-step">
        <div class="descrizione-step">
            <asp:Literal runat="server" ID="ltrDescrizioneStep" />
        </div>

        <div>
            <h3>Dati scadenza</h3>

            <ar:LabeledLabel runat="server" ID="lblMovimento" Value='<%# DataBinder.Eval(DataSource,"NomeAttivita") %>' Label="Attività" />
            <ar:LabeledLabel runat="server" ID="lblDataMovimento" Value='<%# DataBinder.Eval(DataSource,"DataAttivita", "{0:dd/MM/yyyy}") %>' Label="Data movimento" />

            <ar:LabeledLabel runat="server" ID="lblProtocolloMovimento" Value='<%# DataBinder.Eval(DataSource,"Protocollo.Numero") %>' Label="Con protocollo n" Visible='<%# DataBinder.Eval(DataSource,"Protocollo.DatiPresenti") %>' />
            <ar:LabeledLabel runat="server" ID="lblDataProtocollo" Value='<%# DataBinder.Eval(DataSource,"Protocollo.Data", "{0:dd/MM/yyyy}") %>' Label="Del" Visible='<%# DataBinder.Eval(DataSource,"Protocollo.DatiPresenti") %>' />

            <ar:LabeledLabel runat="server" ID="lblProcedimento" Value='<%# DataBinder.Eval(DataSource,"Procedimento") %>' Label="Procedimento" Visible='<%# DataBinder.Eval( DataSource,"HaProcedimento" ) %>' />
            <ar:LabeledLabel runat="server" ID="lblAmministrazione" Value='<%# DataBinder.Eval(DataSource,"Amministrazione") %>' Label="Amministrazione" Visible='<%# DataBinder.Eval( DataSource,"HaAmministrazione" ) %>' />


            <ar:LabeledLabel runat="server" ID="LabeledLabel1"
                Value='<%# DataBinder.Eval(DataSource,"Esito") %>'
                Label="Esito"
                Visible='<%# (bool)DataBinder.Eval( DataSource,"Pubblica" ) && (bool)DataBinder.Eval( DataSource,"PubblicaEsito" ) %>' />

            <ar:LabeledLabel runat="server" ID="LabeledLabel2"
                Value='<%# DataBinder.Eval(DataSource,"Oggetto") %>'
                Label="Oggetto"
                Visible='<%# (bool)DataBinder.Eval( DataSource,"Pubblica" ) && (bool)DataBinder.Eval( DataSource,"PubblicaOggetto" ) %>' />

            <ar:LabeledLabel runat="server" ID="LabeledLabel3"
                Value='<%# DataBinder.Eval(DataSource,"Note") %>'
                Label="Note"
                Visible='<%# (bool)DataBinder.Eval( DataSource,"Pubblica" ) && PubblicaNote %>' />


            <ar:LabeledLabel runat="server" ID="LabeledLabel4"
                Value='<%# GetAttivitaRichiesta() %>'
                Label="Si richiede la seguente attività" />

        </div>

        <div runat="server" visible='<%# Convert.ToInt32( DataBinder.Eval( DataSource , "SchedeDinamiche.Count" ) ) > 0 %>'>
            <h3>Schede da compilare</h3>

            <asp:Repeater runat="server" ID="rptSchedeMovimento"
                DataSource='<%# DataBinder.Eval( DataSource, "SchedeDinamiche" ) %>'>

                <HeaderTemplate>
                    <ul>
                </HeaderTemplate>

                <ItemTemplate>
                    <li>
                        <asp:Literal runat="server" ID="ltrNomeScheda" Text='<%# Eval("NomeScheda") %>' />
                    </li>
                </ItemTemplate>

                <FooterTemplate>
                    </ul>
                </FooterTemplate>
            </asp:Repeater>
        </div>

        <div runat="server" visible='<%# Convert.ToInt32(DataBinder.Eval( DataSource , "Allegati.Count" )) > 0 %>'>

            <h3>Allegati del movimento</h3>

            <asp:GridView ID="dgAllegatiMovimento" runat="server"
                GridLines="None"
                AutoGenerateColumns="False"
                DataSource='<%# DataBinder.Eval( DataSource, "Allegati" ) %>'
                CssClass="table">
                <Columns>
                    <asp:BoundField HeaderText="Descrizione" DataField="Descrizione" />
                    <asp:BoundField HeaderText="Note" DataField="Note" />
                    <asp:TemplateField>
                        <ItemTemplate>
                            <asp:HyperLink runat="server" ID="lnkMostraAllegato" NavigateUrl='<%# DataBinder.Eval( Container.DataItem, "IdAllegato", "~/MostraOggetto.ashx?idComune=" + IdComune + "&CodiceOggetto={0}" ) %>' Target="_blank" Text='Scarica' />
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>
                <EmptyDataTemplate>
                    <div>Nel movimento di origine non sono presenti allegati</div>
                </EmptyDataTemplate>
            </asp:GridView>
        </div>


        <div class="bottoni" id="bottoniMovimento">
            <asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
        </div>
    </div>
</asp:Content>
