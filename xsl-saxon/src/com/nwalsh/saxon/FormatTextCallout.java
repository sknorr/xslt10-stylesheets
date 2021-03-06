package com.nwalsh.saxon;

import com.icl.saxon.om.NamePool;
import com.icl.saxon.output.Emitter;

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

public class FormatTextCallout extends FormatCallout {
  public FormatTextCallout(NamePool nPool, boolean fo, boolean xhtml) {
    super(nPool, fo, xhtml);
  }

  public void formatCallout(Emitter rtfEmitter,
			    Callout callout) {
    formatTextCallout(rtfEmitter, callout);
  }
}
