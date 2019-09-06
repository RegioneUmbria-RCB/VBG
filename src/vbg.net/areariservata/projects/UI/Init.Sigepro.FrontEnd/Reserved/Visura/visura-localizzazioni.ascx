<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-localizzazioni.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_localizzazioni" %>
        <asp:GridView GridLines="None" runat="server" CssClass="table" ID="dgLocalizzazioni" AutoGenerateColumns="false">
            <Columns>
                <asp:TemplateField HeaderText="Indirizzo" ItemStyle-Width="45%">
                    <ItemTemplate>
                        <asp:Literal runat="server" ID="Literal1" Text='<%# DataBinder.Eval(Container.DataItem , "Stradario.PREFISSO") %>' />&nbsp;
						    <asp:Literal runat="server" ID="Literal2" Text='<%# DataBinder.Eval(Container.DataItem , "Stradario.DESCRIZIONE") %>' />&nbsp;
                    </ItemTemplate>
                </asp:TemplateField>

                <asp:BoundField DataField="Civico" HeaderText="Civ." ItemStyle-Width="5%" />
                <asp:BoundField DataField="Esponente" HeaderText="Esp." ItemStyle-Width="5%" />
                <asp:BoundField DataField="Colore" HeaderText="Col." ItemStyle-Width="5%" />
                <asp:BoundField DataField="Scala" HeaderText="Sca." ItemStyle-Width="5%" />
                <asp:BoundField DataField="Piano" HeaderText="Piano" ItemStyle-Width="5%" />
                <asp:BoundField DataField="Interno" HeaderText="Int." ItemStyle-Width="5%" />
                <asp:BoundField DataField="ESPONENTEINTERNO" HeaderText="Esp.Int." ItemStyle-Width="5%" />
                <asp:BoundField DataField="FABBRICATO" HeaderText="Fab." ItemStyle-Width="5%" />

                <asp:TemplateField HeaderText="Località/Frazione" ItemStyle-Width="15%">
                    <ItemTemplate>
                        <asp:Literal runat="server" ID="Literal3" Text='<%# DataBinder.Eval(Container.DataItem , "Stradario.LOCFRAZ") %>' />
                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>
            <EmptyDataTemplate>
                <asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti localizzazioni</i></asp:Label>
            </EmptyDataTemplate>
        </asp:GridView>

        <asp:GridView GridLines="None" runat="server" ID="dgDatiCatastali" CssClass="table" AutoGenerateColumns="false">
            <Columns>
                <asp:TemplateField HeaderText="Tipo catasto">
                    <ItemTemplate>
                        <asp:Literal runat="server" ID="ltrTipoCatasto" Text='<%# DataBinder.Eval(Container.DataItem , "Catasto.DESCRIZIONE")%>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="Foglio" HeaderText="Foglio" />
                <asp:BoundField DataField="Particella" HeaderText="Particella" />
                <asp:BoundField DataField="Sub" HeaderText="Subalterno" />
            </Columns>
            <EmptyDataTemplate>
                <asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti dati catastali</i></asp:Label>
            </EmptyDataTemplate>
        </asp:GridView>