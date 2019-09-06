<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="BoxComune.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Sir.BoxComune" %>
<div class="sir-box-comune">
    <img src="<%= UrlLogo %>">
    <div class="sir-dati-comune">
        <div class="sir-nome-comune">
            <span class="titolo"><%= DataSource.NomeComune %></span><br />
            <span class="sottotitolo"><%= DataSource.NomeComune2 %></span>
        </div>
                        
        <div class="sir-riferimenti-comune">
            <span class="label">Responsabile:</span><%=DataSource.NomeResponsabile%><br/>
            <span class="label">PEC:</span><%= DataSource.IndirizzoPec %><br/>
            <span class="label">Telefono:</span><%= DataSource.Telefono %><br/>
        </div>
    </div>
</div>