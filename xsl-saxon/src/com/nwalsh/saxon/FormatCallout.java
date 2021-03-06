package com.nwalsh.saxon;

import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;

import com.icl.saxon.om.NamePool;
import com.icl.saxon.output.Emitter;
import com.icl.saxon.tree.AttributeCollection;

import com.nwalsh.saxon.Callout;

/**
 * <p>Utility class for the Verbatim extension (ignore this).</p>
 *
 * <p>$Id$</p>
 *
 * <p>Copyright (C) 2000, 2001 Norman Walsh.</p>
 *
 * <p><b>Change Log:</b></p>
 * <dl>
 * <dt>1.0</dt>
 * <dd><p>Initial release.</p></dd>
 * </dl>
 *
 * @author Norman Walsh
 * <a href="mailto:ndw@nwalsh.com">ndw@nwalsh.com</a>
 *
 * @see Verbatim
 *
 * @version $Id$
 **/

public abstract class FormatCallout {
  protected static final String foURI = "http://www.w3.org/1999/XSL/Format";
  protected static final String xhURI = "http://www.w3.org/1999/xhtml";
  protected String uri = "";
  protected boolean foStylesheet = false;
  protected boolean xhStylesheet = false;
  protected NamePool namePool = null;

  public FormatCallout(NamePool nPool, boolean fo, boolean xhtml) {
    namePool = nPool;
    foStylesheet = fo;
    xhStylesheet = xhtml;
  }

  public String areaLabel(Element area) {
    String label = null;

    if (area.hasAttribute("label")) {
      // If this area has a label, use it
      label = area.getAttribute("label");
    } else {
      // Otherwise, if its parent is an areaset and it has a label, use that
      Element parent = (Element) area.getParentNode();
      if (parent != null
	  && parent.getLocalName().equalsIgnoreCase("areaset")
	  && parent.hasAttribute("label")) {
	label = parent.getAttribute("label");
      }
    }

    return label;
  }

  // Get ID (used for xrefs)
  public String areaID(Element area) {
    String id = null;
    
    if (area.hasAttribute("id")) {
      id = area.getAttribute("id");
    }

    else {
      if (area.hasAttribute("xml:id")) {
	id = area.getAttribute("xml:id");
      } 
    
      else {
	id = "";
      }
    }
    //System.out.println(id);
    return id;
  }
  
  public void startSpan(Emitter rtf, String id)
    throws TransformerException {
       
    if (!foStylesheet && namePool != null) {
      if(xhStylesheet) {
	uri = xhURI;
      }
      
  
      int spanName = namePool.allocate("", uri, "span");

      AttributeCollection spanAttr = new AttributeCollection(namePool);
      int namespaces[] = new int[1];
      spanAttr.addAttribute("", uri, "class", "CDATA", "co");
      spanAttr.addAttribute("", uri, "id", "CDATA", id);
      rtf.startElement(spanName, spanAttr, namespaces, 0);
    }
  }

  public void endSpan(Emitter rtf)
    throws TransformerException {

    if (!foStylesheet && namePool != null) {
      if (xhStylesheet) {
	uri = xhURI;
	}
      int spanName = namePool.allocate("", uri, "span");
      rtf.endElement(spanName);
    }
  }

  public void formatTextCallout(Emitter rtfEmitter,
				Callout callout) {
    Element area = callout.getArea();
    int num = callout.getCallout();
    String userLabel = areaLabel(area);
    String id = areaID(area);
    String label = "(" + num + ")";

    if (userLabel != null) {
      label = userLabel;
    }

    char chars[] = label.toCharArray();

    try {
      startSpan(rtfEmitter, id);
      
      if (foStylesheet) {
	int inlineName = namePool.allocate("fo", foURI, "inline");
	AttributeCollection inlineAttr = new AttributeCollection(namePool);
	int namespaces[] = new int[1];
	inlineAttr.addAttribute("", "", "id", "CDATA", id);
	rtfEmitter.startElement(inlineName, inlineAttr, namespaces, 0);
    }
      rtfEmitter.characters(chars, 0, label.length());
      endSpan(rtfEmitter);
      
      if (foStylesheet) {
	int inlineName = namePool.allocate("fo", foURI, "inline");
	rtfEmitter.endElement(inlineName);
      }
      
    } catch (TransformerException e) {
      System.out.println("Transformer Exception in formatTextCallout");
    }
  }
  
  public abstract void formatCallout(Emitter rtfEmitter,
				     Callout callout);
}

