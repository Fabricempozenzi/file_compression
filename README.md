# file_compression


#DESIGN

#SchubsH

SchubsH is designed using the Huffman coding algorithm. It works by creating a binary tree of nodes that represent the frequency of each data item. The most frequent items are closer to the root of the tree, which allows them to be encoded with fewer bits. The trade-off with Huffman coding is that it requires a full pass over the data to build the frequency table before it can start encoding. This can be a disadvantage for very large datasets. However, for most applications, the improved compression ratio outweighs the extra time required to build the frequency table.

#SchubsL

SchubsL is designed using the LZW (Lempel-Ziv-Welch) compression algorithm. The LZW algorithm works by creating a dynamic dictionary of strings encountered in the input data. The dictionary is initialized with individual characters and as the data is processed, longer sequences are added to the dictionary. When a sequence is encountered that is already in the dictionary, the code for that sequence is output and a new sequence one character longer is added to the dictionary.
In the context of SchubsL, the dictionary is implemented using a Ternary Search Trie (TST). A TST is a type of trie that is efficient for queries like "What is the longest prefix of the query string that is in the dictionary?". This makes it a good fit for the LZW algorithm.

The trade-off with LZW compression is that it can potentially use a lot of memory, as the dictionary can become quite large. However, it is very effective for files with repeated sequences. In SchubsL, the dictionary size is limited to 256 entries, which helps to control memory usage.

SchubsL uses a fixed-width output of 8 bits, which means that each output symbol is represented by exactly 8 bits. This makes the output size predictable, but it may not be the most efficient representation if the input data has a skewed distribution.The compress method in SchubsL reads the input file, compresses it using the LZW algorithm, and writes the compressed data to the output file. The main method processes command line arguments, checks if the output file already exists, and calls the compress method for each input file.

#SchubsArc

SchubsArc is designed to create an archive file from a set of input files. It uses the LZW (Lempel-Ziv-Welch) compression algorithm, implemented in the SchubsL class, to compress each input file before adding it to the archive.
The archive file layout is as follows: file-name-length, separator, filename, file-size, file. Each of these elements is separated by a special separator character (all ones 11111111). This layout allows the archive file to be easily parsed and decompressed later.
The main trade-off in SchubsArc's design is between compression efficiency and complexity. By compressing each file individually before adding it to the archive, SchubsArc ensures that the archive file is as small as possible. However, this also means that decompressing the archive file is more complex, as each file must be decompressed individually.

#Deschubs

The Deschubs program is designed to decompress files that have been compressed using Huffman or LZW (Lempel-Ziv-Welch) algorithms, or archived using a simple tar-like format. Different methods are called based on the the type of a file you want to decompress. 

#TEST: 

For testing, you would want to ensure that the compression and decompression, tar and untar work correctly. This could involve compressing a file, decompressing it, and then comparing the original and decompressed files to ensure they are identical. You would also want to test edge cases such as empty files or files with only one unique character, or files with only lower cases letter. They are separate files that have edge cases for all algorithms highlighted above. 
 #TEST INSTRUCTION: Run mvn test

# RUN INSTALLATIONS:

clone the repository: git clone https://github.com/Fabricempozenzi/file_compression.git 
run mvn compile to compile the project

#RUN EXAMPLE:

#SchubsH:

java -cp target/classes SchubsH src/test/resources/*.txt for all .txt files or java -cp target/classes SchubsH src/test/resources/file1.txt

#SchubsL: 

java -cp target/classes SchubsH src/test/resources/*.txt for all .txt files or java -cp target/classes SchubsH src/test/resources/file1.txt for a file named (file1.txt)

#SchubsArch:

java -cp target/classes SchubsArc src/test/resources/blee  src/test/resources/*.txt (This would create an archive file named blee.zl that includes all compressed .txt files)

#Deschubs:

java -cp target/classes Deschubs src/test/resources/*.hh  or *.ll or zl
