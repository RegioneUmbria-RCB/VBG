<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>

	<!-- The template to display files available for upload -->
	<script id="cart-template-upload" type="text/x-jquery-tmpl">
	{{each( i, file ) files}}
    <tr class="template-upload fade">
        <td class="name">
			<span>{{= file.name}}</span>
			<input type="hidden" class="facct_internal" name="" id="" value="{{= file.codiceoggetto}}"/>
		</td>
		{{if file.error}}
        	<td class="cancel" colspan="2">
			{{if !i}}
            	<button class="btn btn-warning">
                	<i class="icon-ban-circle icon-white"></i>
                	<span>Annulla</span>
            	</button>
        	{{/if}}
			</td>
			<td class="error" colspan="2"><span class="label label-important">Error</span> {{= file.error}}</td>
		{{else $data.files.valid && !i}}
            <td class="start">
			{{if !$data.options.autoUpload}}
                <button class="btn btn-primary">
                    <i class="icon-upload icon-white"></i>
                    <span>Carica</span>
                </button>
            {{/if}}
			</td>
        	<td class="cancel" colspan="2">
            	<button class="btn btn-warning">
                	<i class="icon-ban-circle icon-white"></i>
                	<span>Annulla</span>
            	</button>
			</td>
			<td class="size"><span>{{= $data.formatFileSize(file.size)}}</span></td>
            <td>
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
					<div class="bar" style="width:0%;"></div>
				</div>
            </td>
        {{else}}
            <td colspan="4"></td>
        {{/if}}
    </tr>
	{{/each}}
	</script>
	<!-- The template to display files available for download -->
	<script id="cart-template-download" type="text/x-jquery-tmpl">
	{{each( i, file ) files}}
    <tr class="template-download">
        {{if (file.error)}}
        	<td class="name">
				<span>{{= file.name}}</span>
			</td>
	        <td class="delete">
    	        <button class="btn btn-danger" data-type="{{= file.deleteType}}" data-url="{{= file.deleteUrl}}">
        	        <i class="icon-trash icon-white"></i>
            	    <span>Elimina</span>
     	       </button>
        	</td>
            <td class="size"><span>{{= $data.formatFileSize(file.size)}}</span></td>
            <td class="error" colspan="2"><span class="label label-important">{{= file.error}}</span></td>
        {{else}}
            <td class="name">
                <a href="{{= file.url}}" title="{{= file.name}}" download="{{= file.name}}">{{= file.name}}</a>
				<input type="hidden" name="{{= $data.options.inputName}}" id="{{= $data.options.idSemantico}}" value="{{= file.codiceOggetto}}"/>
				<input type="hidden" name="{{= $data.options.inputName}}_filename" id="{{= $data.options.idSemantico}}_filename" value="{{= file.name}}"/>
            </td>
	        <td class="delete">
    	        <button class="btn btn-danger" data-type="{{= file.deleteType}}" data-url="{{= file.deleteUrl}}">
        	        <i class="icon-trash icon-white"></i>
            	    <span>Elimina</span>
     	       </button>
        	</td>
            <td class="size"><span>{{= $data.formatFileSize(file.size)}}</span></td>
            <td class="warning" colspan="2">
			{{if (file.dserror)}}
					<span class="warning_image" title="{{= file.dserror}}">
						<label >(!)</label>
	                </span>
			{{/if}}
			</td>
		{{/if}}
    </tr>
	{{/each}}
	</script>
	
