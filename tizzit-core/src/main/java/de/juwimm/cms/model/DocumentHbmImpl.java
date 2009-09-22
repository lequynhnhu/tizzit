/**
 * Copyright (c) 2009 Juwi MacMillan Group GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package de.juwimm.cms.model;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.tizzit.util.Base64;

import de.juwimm.cms.vo.DocumentSlimValue;

/**
 * @see de.juwimm.cms.model.DocumentHbm
 * @author <a href="mailto:carsten.schalm@juwimm.com">Carsten Schalm</a>
 * , Juwi|MacMillan Group Gmbh, Walsrode, Germany
 * @version $Id$
 */
public class DocumentHbmImpl extends DocumentHbm {
	private static final long serialVersionUID = 4804431489081379254L;
	private static Logger log = Logger.getLogger(DocumentHbmImpl.class);

	/**
	 * @see de.juwimm.cms.model.DocumentHbm#getSlimValue()
	 */
	@Override
	public DocumentSlimValue getSlimValue() {
		DocumentSlimValue vo = new DocumentSlimValue();
		vo.setDocumentId(this.getDocumentId());
		vo.setDocumentName(this.getDocumentName());
		vo.setMimeType(this.getMimeType());
		vo.setTimeStamp(this.getTimeStamp());
		vo.setUseCountLastVersion(this.getUseCountLastVersion());
		vo.setUseCountPublishVersion(this.getUseCountPublishVersion());
		return vo;
	}
	
}
