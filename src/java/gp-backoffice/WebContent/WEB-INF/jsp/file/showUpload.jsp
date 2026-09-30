<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>

<fieldset style="background-color: #fff ! important"><legend><fmt:message key="form.oggetti.upload.legend" /></legend> 
		
		<br class="clear" />
			<ul class="listaSchede">
				<li><a id="uploadFileTabHref${param.idElemento}" class="SchedaAttiva" href="javascript:showTab${param.idElemento}('file');"><fmt:message key="form.oggetti.upload.tab.file" /></a></li>
				<c:if test="${!param.isLibreria}">
					<li><a id="uploadLibraryTabHref${param.idElemento}" class="Scheda" href="javascript:showTab${param.idElemento}('library');"><fmt:message key="form.oggetti.upload.tab.library" /></a></li>
				</c:if>
				<c:if test="${vertScanner==true}">	
					<li><a id="uploadScannerTabHref${param.idElemento}" class="Scheda"  href="javascript:showTab${param.idElemento}('scanner');"><fmt:message key="form.oggetti.upload.tab.scanner" /></a></li>
				</c:if>
			</ul>
		<br />			
<div id="uploadFileTab${param.idElemento}">		
		<c:if test="${!param.isLibreria}">
			<div>
				<fmt:message key="form.oggetti.upload.tab.file.message" />
			</div>
	
			<ol>
				
				<li>
					<label for="libreriachk${param.idElemento}"><input id="libreriachk${param.idElemento}" type="checkbox" onclick="showLibrary${param.idElemento}(this);" value="true"/>
					<fmt:message key="form.oggetti.upload.add.library" /></label>
					<br /><fmt:message key="form.oggetti.upload.add.file.library.message" />
					<div id="libreriachkDiv${param.idElemento}" style="display: none;">
						<table>
						<tr>
							<td><fmt:message key="form.oggetti.upload.library.tipologia" /></td>
							<td>
								<select name="tipologiaOggettoUpload" id="tipologiaOggettoUpload">
									<option value=""><fmt:message key="label.select.default" /></option>
									<c:forEach items="${listaTipologie}" var="tipologia">
										<option value="${tipologia.id.codice}">${tipologia.descrizionetipologia}</option>							
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td><fmt:message key="form.oggetti.upload.library.descrizione" /></td>
							<td>
								<input type="text" name="descrizioneUpload" id="descrizioneUpload" size="50"/>
							</td>
						</tr>
						</table>
					</div>
				</li>				
				<li>			
					<span id="objupload${param.idElemento}" class="upload" style="border: medium; border-color: black;" onmouseover="uploadThis${param.idElemento}();">			
						<a class="vbg-btn btn-aggiungi" id="button${param.idElemento}">
						</a>
						<b><fmt:message key="form.oggetti.upload.choose.file" /></b>
						<p id="uploadText${param.idElemento}"></p>
					</span>
				</li>	
			</ol>		
		</c:if>
		<c:if test="${param.isLibreria}">
			<div>
				<fmt:message key="form.oggetti.upload.tab.file.message_nolibreria" />
			</div>
			<input type="file" name="fileUpload" />
						
		</c:if>			

</div>
<c:if test="${!param.isLibreria}">
<div id="uploadLibraryTab${param.idElemento}" style="display: none;">
		<div>
			<fmt:message key="form.oggetti.upload.add.library.message" />	
		</div>
		<div class="clear">&nbsp;</div>
				
					<table>
					<tr>
						<td><fmt:message key="form.oggetti.upload.library.tipologia" /></td>
						<td><select name="tipologiaOggettoUpload_2" id="tipologiaOggettoUpload_2" onchange="createAjaxCompleter${param.idElemento}('libreriaFile${param.idElemento}_id','libreriaFile${param.idElemento}_id_choices');">
								<option value=""><fmt:message key="label.select.default" /></option>
								<c:forEach items="${listaTipologie}" var="tipologia">
									<option value="${tipologia.id.codice}">${tipologia.descrizionetipologia}</option>							
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr id="tr_libreriaFile${param.idElemento}_id" style="display: none;">
						<td><fmt:message key="form.oggetti.upload.library.descrizione" /></td>
						<td>
							<input type="text" name="libreriaFile" size="50" id="libreriaFile${param.idElemento}_id" class="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'${param.codiceOggettoId}')"/>
							<div id="libreriaFile${param.idElemento}_id_choices" class="autocomplete"></div>
						</td>
					</tr>
					</table>
					<p id="uploadTextLibrary${param.idElemento}"></p>
				
</div>
</c:if>
<c:if test="${vertScanner==true}">	
<div id="uploadScannerTab${param.idElemento}" style="display: none;">


</div>
</c:if>
				<br class="clear"/>
				<span id="functions">			
					<ul>
						<li><a href="javascript: void 0" onclick="getDefaultUploadMsg${param.idElemento}();return false;"><fmt:message key="form.oggetti.upload.reset" /></a></li>
					</ul>
				</span> 
		<br class="clear"/>&nbsp;		 
		</fieldset>
