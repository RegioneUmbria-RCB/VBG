package it.gruppoinit.pdfutils.domain;

import it.gruppoinit.pdfutils.schemas.messages.Font;
import it.gruppoinit.pdfutils.schemas.messages.Layer;

public class LayerHelper {

    private Layer layer;
    private Font font;

    public Layer getLayer() {

	return layer;
    }

    public void setLayer(Layer layer) {

	this.layer = layer;
    }

    public Font getFont() {

	return font;
    }

    public void setFont(Font font) {

	this.font = font;
    }
}
