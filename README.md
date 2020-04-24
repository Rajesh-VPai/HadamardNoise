## Welcome to GitHub Pages

You can use the [editor on GitHub](https://github.com/Rajesh-VPai/TaguchiHadamardNoise/edit/master/README.md) to maintain and preview the content for your website in Markdown files.

Whenever you commit to this repository, GitHub Pages will run [Jekyll](https://jekyllrb.com/) to rebuild the pages in your site, from the content in your Markdown files.

### Markdown

Markdown is a lightweight and easy-to-use syntax for styling your writing. It includes conventions for
[Link](https://github.com/Rajesh-VPai/HadamardNoise)
#### About TaguchiHadamardNoise
TaguchiHadamardNoise is a core Java orthogonal Array Matrix Generator.
It can generate Taguchi as well as Hadamard Noise Orthogonal Array Matrix. It contains an example of how to self test itself using Orthogonal Array for Testing. It contains copious documentation on the code. It contains an application of DOE Programming (DP) similar to Genetic Programming (GP). It is used to generate random numbers with a small set of config files.

Code does not throw any Java exception.
- TaguchiHadamardNoise allows full degrees of freedom to the User. The User can generate an OA for any number of runs(rows), any factors (columns) and any LEVELS. It supports Hadamard MultiLevel Matrices (Level > 2)
- At the core, the TaguchiHadamardNoise uses a simple pseudo DPRNG counter to generate the Full factorial OA. It also has a  algorithm function that generates pseudo algorithmic (but not full factorial nor pseudo random) cell value. The cell value is reffered to as partial OA.
- The TaguchiHadamardNoise flags off if the runs are unique or duplicate.
- It also calculates the optimal Length of Message for a given run (row or k) for the given levels.(Lengthrecommended).

- Development and Test Environment: Windows XP and Java 7 & Netbeans 8.2

##### Project Directory Structure
Available in TaguchiHadamardNoise.zip
- build
- data
- logs
- nbproject
- src

data Directory Structure
- DOEMatrix : For CSV File  OA (not used)
- DOESelfTestConfig: For TaguchiHadamardNoise Regression Testing  Config Files: Run the function(method):mainRegresssionDOE
- DOEDPFine : For DOE Programming (akin to Genetic Programming by Koza)
Files in data Directory (Base):DOEHadamardDefault

Files in DOESelfTestConfig Directory: All Files DOEHadamard000 to DOEHadamard029 + DOEHadamardDefault

Files in DOEDPFine : All files DOEDPNoise000 to DOEDPNoise010 + DOEDPNoiseDefault
###### User Instructions
The User can use TaguchiHadamardNoise to generate his/her full factorial / partial Orthogonal Array ( Taguchi/Hadamard).Once the Noise Matrix is generated, the user can proceed with the experiments. This software does not do the analysis. The user can use Microsoft Excel or any other software. To compute the DOE Signal /Noise Ratios.

In certain conditions. the 100% Full factorial OA might not be obtained. In such cases, the user has to manually make the appropriate changes. For help and guidance,the user can refer to the level (strength) analysis at the bottom of the screen/output. The user has to infer which cell has to change.
All column strengths should be 100% in case of a full factorial OA.
Hadamard OA is Taguchi OA with level=2.


In total, for the User to do his/her DOE experiments, the user has to use DOEMATRIXGEN [Link](https://github.com/Rajesh-VPai/DOEMatrixGen) as well as the noise OA (TaguchiHadamardNoise).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/Rajesh-VPai/TaguchiHadamardNoise/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://help.github.com/categories/github-pages-basics/) or [contact support](https://github.com/contact) and we’ll help you sort it out.
