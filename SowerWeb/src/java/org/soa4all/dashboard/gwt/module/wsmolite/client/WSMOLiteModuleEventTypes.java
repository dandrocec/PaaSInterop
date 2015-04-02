/**
 Copyright (c) 2009, Ontotext AD
 
 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License along
 with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 */

package org.soa4all.dashboard.gwt.module.wsmolite.client;

public class WSMOLiteModuleEventTypes {
    private static final int OFFSET = 999000;

    public final static int SHOW_INFO = OFFSET + 1;
    
    public final static int MENU_OPEN_ONTOLOGY = OFFSET + 2;
    public final static int MENU_OPEN_SERVICE = OFFSET + 3;
    public final static int MENU_SAVE_SERVICE = OFFSET + 4;
    public final static int MENU_CLOSE = OFFSET + 5;
    
    public final static int MENU_ABOUT = OFFSET + 6;

}
