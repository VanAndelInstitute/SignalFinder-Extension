This is the source code for the SignalFinder Extension for QuPath. 

The GUI is based off of the Instaseg GUI (https://github.com/qupath/qupath-extension-instanseg/tree/main)

<h1>What is SignalFinder?</h1>
As the name implies, SignalFinder is a tool for finding signal (thresholding) in immunoflourescense images with respect to background.  

SignalFinder works through the use of image texture analysis theory, relying patterns in the statistical variation of small regions of image in common between background regions of different IF images. 
Notably, while mean, median, and standard deviation may vary in background regions between images, coeffecient of variation (CV) is consistent.

The SignalFinder Extension is based off of the SFT algortihm (doi.org/10.1021/acs.analchem.5b03159)

Unlike the original SFT algorithm, however, the SignalFinder extension does not using a sliding window analysis to sample the image statistics. Rather, it uses the cell measurements(mean, sd, median) from cell compartments (Nucleus, Cytoplasm, and Membrane) to sample the image statistics. This approach has been validated to give results with a high degree of agreement with the original algorithm.

<h1>Why SignalFinder?</h1>
Image thresholding has long been a tool in the image analyst's toolbelt, though in many cases thresholds are determined manually (by eye). 
Automated thresholding options such as Otsu's method and the Triangle method are popular but are known to fail when the distribution of signal fails to meet the assumptions of the method.
SignalFinder, on the other hand, has more robust assumptions about signal, only requiring some amount of background to be present in the subject of the image.

<h1>How to install?</h1>

<h1>How to use?</h1>
