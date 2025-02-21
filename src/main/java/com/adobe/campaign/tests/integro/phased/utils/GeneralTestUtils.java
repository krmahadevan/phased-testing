/*
 * MIT License
 *
 * © Copyright 2020 Adobe. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.adobe.campaign.tests.integro.phased.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.adobe.campaign.tests.integro.phased.PhasedTestManager;

/**
 * Methods dedicated to manage resources used by tests
 * 
 * 
 * Author : gandomi
 *
 */
public class GeneralTestUtils {

    protected static Logger log = LogManager.getLogger();

    /**
     * Creates a cache directory under the standard output directory, which is
     * by default {@value PhasedTestManager#DEFAULT_CACHE_DIR }
     *
     * Author : gandomi
     *
     * @param in_directoryName
     *        A simple name under which we will store cache data
     * @return An existing cache directory object
     *
     */
    public static File createCacheDirectory(String in_directoryName) {
        if (in_directoryName == null || in_directoryName.isEmpty()) {
            throw new IllegalArgumentException(
                    "The given argument 'in_directoryName' cannot be null nor empty.");
        }

        File lr_cacheDir = new File(PhasedTestManager.STD_CACHE_DIR, in_directoryName);
        if (!lr_cacheDir.exists()) {
            lr_cacheDir.mkdirs();
        }
        return lr_cacheDir;
    }

    /**
     * Returns a cache directory with the given name. It will create it, if not
     * present.
     *
     * Author : gandomi
     *
     * @param in_directoryName
     *        A cache directory name
     * @return An existing cache directory object
     *
     */
    public static File fetchCacheDirectory(String in_directoryName) {
        return createCacheDirectory(in_directoryName);
    }

    /**
     * Generates an empty file in the given cache directory. If the file exists
     * it is overwritten.
     *
     * Author : gandomi
     *
     * @param in_cacheDir
     *        A cache directory
     * @param in_fileName
     *        A name for the file to create
     * @return a file with the given name and stored in the given cache
     *         directory
     * @throws IllegalArgumentException
     *         when the given file name or directory are null
     *
     */
    public static File createEmptyCacheFile(File in_cacheDir, String in_fileName) {
        if (in_cacheDir == null) {
            throw new IllegalArgumentException("The given argument 'in_cacheDir' cannot be null nor empty.");
        }

        if (in_fileName == null || in_fileName.isEmpty()) {
            throw new IllegalArgumentException("The given argument 'in_fileName' cannot be null nor empty.");
        }

        File l_testFile = new File(in_cacheDir, in_fileName);

        deleteFile(l_testFile);
        return new File(in_cacheDir, in_fileName);
    }

    /**
     * This method deletes a given file. If
     *
     * Author : gandomi
     *
     * @param in_fileToDelete
     *        A file that we want to delee.
     *
     */
    protected static void deleteFile(File in_fileToDelete) {
        if (in_fileToDelete.exists()) {
            log.debug("Deleting cache File");
            if (!in_fileToDelete.delete()) {
                throw new IllegalStateException("Unable to delete file " + in_fileToDelete.getPath());
            }
        }
    }

    /**
     * This method fetches the lines of a given file.
     * 
     * Author : gandomi
     *
     * @param in_resourceFile
     *        A file object
     * @return A list where each entry is a line in the file
     *
     */
    public static List<String> fetchFileContentLines(File in_resourceFile) {
        List<String> lr_listOfFlaggedTests = new ArrayList<>();

        //Parse file as one entry per line
        try (Scanner s = new Scanner(new FileInputStream(in_resourceFile))) {

            while (s.hasNextLine()) {
                lr_listOfFlaggedTests.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }

        return lr_listOfFlaggedTests;
    }

    /**
     * This method fetches the contents of a file as a string
     *
     * Author : gandomi
     *
     * @param in_resourceFile
     *        A file object
     * @return A string representing the contents of that file
     *
     */
    public static String fetchFileContent(File in_resourceFile) {
        if (!in_resourceFile.exists()) {
            throw new IllegalArgumentException(
                    "The given file " + in_resourceFile.getPath() + " does not exist.");
        }

        StringBuilder lr_fileContent = new StringBuilder();

        //Parse file as one entry per line
        try (Scanner s = new Scanner(new FileInputStream(in_resourceFile))) {

            while (s.hasNextLine()) {
                lr_fileContent.append(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }

        return lr_fileContent.toString();
    }

    /**
     * This method fetches the lines with actual data of a given file. In this
     * case we also filter the commented out lines
     * 
     * Author : gandomi
     *
     * @param in_resourceFile
     *        A file object
     * @return A list where each entry is a an uncommented line in the file
     *
     */
    public static List<String> fetchFileContentDataLines(File in_resourceFile) {

        return fetchFileContentLines(in_resourceFile).stream().filter(l -> !l.startsWith("#")).collect(Collectors.toList());
    }

}
