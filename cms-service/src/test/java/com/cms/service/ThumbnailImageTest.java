package com.cms.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ThumbnailImageTest {
    int width=100;
    int height=120;
    @Test
    public void  testThumbnailImage() throws IOException{
        BufferedImage originalImage = ImageIO.read(new File("l:/1.jpg"));
        Thumbnails.of(originalImage)
                .scale((double)width/(double)originalImage.getWidth())
                .toFile(new File("l:/2.jpg"));;
    }
}
