/*******************************************************************************
 * Copyright (c) 2010  Ola Spjuth <ola@bioclipse.net>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.browser.tests;

import static org.junit.Assert.*;

import java.util.List;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IBioObject;
import net.bioclipse.jobs.BioclipseJob;
import net.bioclipse.jobs.BioclipseJobUpdateHook;
import net.bioclipse.browser.business.business.IBrowserManager;

import org.junit.Test;

/**
 * 
 * @author ola
 *
 */
public abstract class AbstractBrowserManagerPluginTest {
    
    private final static String pubchem_omeprazole_page = 
       "http://www.ncbi.nlm.nih.gov/sites/entrez?db=pccompound&term=omeprazole";

    protected static IBrowserManager browser;

//    @Test 
    public void testScrapePubchemPageSync() throws BioclipseException {

        List<? extends IBioObject> mols = browser.scrapeWebpage(
                                                       pubchem_omeprazole_page);

        System.out.println("Scraped " + mols.size() + " mols from page.");
        //We don't know how many it is, could vary over time
        assertTrue( mols.size()>3 );
    }

    @Test public void testScrapePubchemPagePartialJob() 
    throws BioclipseException, InterruptedException {

        BioclipseJob<List<? extends IBioObject>> job = 
                                 browser.scrapeWebpage( pubchem_omeprazole_page,  
                                 new BioclipseJobUpdateHook<
                                 List<? extends IBioObject>>("Scraping"){
                                     
                  public void partialReturn( List<? extends IBioObject> mols ) {

                      System.out.println("New scrape of size: " + mols.size());
                      //I guess we should assert something here..
                      int a=0; a++;
                  }
        });
        
        job.join();

    }

}
