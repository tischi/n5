package org.janelia.saalfeldlab.n5;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class UnicodeArrayDataBlock extends AbstractDataBlock<String[]>
{

	private final int numBytesPerString;

	public UnicodeArrayDataBlock( final int[] size, final long[] gridPosition, final String[] data, int numCharsPerString ) {
		super(size, gridPosition, data);
		this.numBytesPerString = numCharsPerString * 4;
	}

	@Override
	public ByteBuffer toByteBuffer() {

		final ByteBuffer buffer = ByteBuffer.allocate(data.length * numBytesPerString);
		buffer.asCharBuffer().put(new char[data.length * numBytesPerString]);
		return buffer;
	}

	private char[] stringArrayToCharArray( String[] data )
	{
		String concatenated = "";
		for (String s : data )
			concatenated += s;
		char[] charArray = concatenated.toCharArray();
		return charArray;
	}

	@Override
	public void readData(final ByteBuffer buffer) {
		for ( int i = 0; i < data.length; i++ )
		{
			final byte[] chars = new byte[ numBytesPerString ];
			buffer.get( chars );
			data[ i ] = new String( chars, Charset.forName("UTF-32"));
		}
	}

	@Override
	public int getNumElements() {

		return data.length;
	}
}
