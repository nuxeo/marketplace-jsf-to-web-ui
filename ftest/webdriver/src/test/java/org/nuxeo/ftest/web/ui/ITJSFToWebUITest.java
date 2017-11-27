/*
 * (C) Copyright 2017 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Thomas Roger
 */
package org.nuxeo.ftest.web.ui;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.common.utils.URIUtils;
import org.nuxeo.functionaltests.AbstractTest;
import org.nuxeo.functionaltests.RestHelper;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

/**
 * @since 9.10
 */
@RunWith(FeaturesRunner.class)
public class ITJSFToWebUITest extends AbstractTest {

    public static final String WORKSPACES_PATH = "/default-domain/workspaces";

    public static final String WORKSPACE_NAME = "Workspace";

    public static final String WORKSPACE_PATH = WORKSPACES_PATH + "/" + WORKSPACE_NAME;

    public static final String JSF_ID_URL = "%s/nxdoc/default/%s/view_documents";

    public static final String JSF_PATH_URL = "%s/nxpath/default%s@view_documents";

    public static final String WEB_UI_ID_URL = "%s/ui/#!/doc/default/%s";

    public static final String WEB_UI_PATH_URL = "%s/ui/#!/browse%s";

    protected String docId;

    @Before
    public void before() {
        docId = RestHelper.createDocument(WORKSPACES_PATH, "Workspace", WORKSPACE_NAME, null);
    }

    @After
    public void after() {
        RestHelper.cleanup();
    }

    @Test
    public void testWebUIRedirect() {
        getLoginPage().login("Administrator", "Administrator");
        String url = String.format(JSF_ID_URL, NUXEO_URL, docId);
        driver.get(url);
        String currentURL = driver.getCurrentUrl();
        String expectedURL = String.format(WEB_UI_ID_URL, NUXEO_URL, docId);
        assertEquals(expectedURL, URIUtils.getURIPath(currentURL));

        url = String.format(JSF_PATH_URL, NUXEO_URL, WORKSPACE_PATH);
        driver.get(url);
        currentURL = driver.getCurrentUrl();
        expectedURL = String.format(WEB_UI_PATH_URL, NUXEO_URL, WORKSPACE_PATH);
        assertEquals(expectedURL, URIUtils.getURIPath(currentURL));
        // logout avoiding JS error check
        driver.get(NUXEO_URL + "/logout");
    }

    @Override
    public void checkJavascriptError() {
        // avoid JS error check for this test
    }
}
