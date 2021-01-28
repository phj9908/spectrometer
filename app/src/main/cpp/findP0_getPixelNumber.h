//
// Created by 1403 on 2020-12-07.
//

#ifndef MY_APPLICATION1125_1_FINDP0_GETPIXELNUMBER_H
#define MY_APPLICATION1125_1_FINDP0_GETPIXELNUMBER_H


#include "opencv2\opencv.hpp"
#include <iostream>
#include <opencv2/imgproc.hpp>
#include <String>
#include <algorithm>
#include <vector>

uchar getMax(Vec3b& value);
int findP0(const Mat& img);
void getMeasurePixelNumber(const vector<double>& wavelength, vector<double>& pixelNumber, int p0, double GRADIENT);

//bool compressY(Mat& img, int& top, int& height); // img: 1-channel mat

#endif //MY_APPLICATION1125_1_FINDP0_GETPIXELNUMBER_H
