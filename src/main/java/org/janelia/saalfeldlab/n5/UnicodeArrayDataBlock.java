package org.janelia.saalfeldlab.n5;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class UnicodeArrayDataBlock extends AbstractDataBlock<String[]>
{

	private final int stringLength;

	public UnicodeArrayDataBlock( final int[] size, final long[] gridPosition, final String[] data, int stringLength ) {
		super(size, gridPosition, data);
		this.stringLength = stringLength;
	}

	@Override
	public ByteBuffer toByteBuffer() {

		final ByteBuffer buffer = ByteBuffer.allocate(data.length * stringLength);
		char[] charArray = stringArrayToCharArray( data );
		buffer.asCharBuffer().put(charArray);
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
			final byte[] chars = new byte[ stringLength ];
			buffer.get( chars );
			data[ i ] = new String( chars, Charset.forName("UTF-8"));
		}
	}

	@Override
	public int getNumElements() {

		return data.length;
	}
}
