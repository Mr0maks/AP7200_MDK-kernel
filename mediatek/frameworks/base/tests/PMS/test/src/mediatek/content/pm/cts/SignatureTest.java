/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein is
 * confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 * the prior written permission of MediaTek inc. and/or its licensors, any
 * reproduction, modification, use or disclosure of MediaTek Software, and
 * information contained herein, in whole or in part, shall be strictly
 * prohibited.
 * 
 * MediaTek Inc. (C) 2010. All rights reserved.
 * 
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 * ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 * RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 * INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 * TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 * RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 * OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 * SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 * RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 * ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 * RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 * MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 * CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek
 * Software") have been modified by MediaTek Inc. All revisions are subject to
 * any receiver's applicable license agreements with MediaTek Inc.
 */

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mediatek.content.pm.cts;

import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetClass;
import dalvik.annotation.TestTargetNew;
import dalvik.annotation.TestTargets;

import android.content.pm.Signature;
import android.os.Parcel;
import android.test.AndroidTestCase;

import java.util.Arrays;

@TestTargetClass(Signature.class)
public class SignatureTest extends AndroidTestCase {

    private static final String mSignatureString = "1234567890abcdef";
    // mSignatureByteArray is the byte code of mSignatureString.
    private static final byte[] mSignatureByteArray = { (byte) 0x12, (byte) 0x34, (byte) 0x56,
            (byte) 0x78, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef };
    // mDiffByteArray has different content to mSignatureString.
    private static final byte[] mDiffByteArray = { (byte) 0xfe, (byte) 0xdc, (byte) 0xba,
            (byte) 0x09, (byte) 0x87, (byte) 0x65, (byte) 0x43, (byte) 0x21 };

    @TestTargets({
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test constructor",
            method = "Signature",
            args = {byte[].class}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test constructor",
            method = "Signature",
            args = {java.lang.String.class}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test toByteArray",
            method = "toByteArray",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test toCharsString",
            method = "toCharsString",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test toChars",
            method = "toChars",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test toChars",
            method = "toChars",
            args = {char[].class, int[].class}
        )
    })
    public void testSignature() {
        Signature signature = new Signature(mSignatureString);
        byte[] actualByteArray = signature.toByteArray();
        assertTrue(Arrays.equals(mSignatureByteArray, actualByteArray));

        signature = new Signature(mSignatureByteArray);
        String actualString = signature.toCharsString();
        assertEquals(mSignatureString, actualString);

        char[] charArray = signature.toChars();
        actualString = new String(charArray);
        assertEquals(mSignatureString, actualString);

        char[] existingCharArray = new char[mSignatureString.length()];
        int[] intArray = new int[1];
        charArray = signature.toChars(existingCharArray, intArray);
        actualString = new String(charArray);
        assertEquals(mSignatureString, actualString);
        // intArray[0] represents the length of array.
        assertEquals(intArray[0], mSignatureByteArray.length);
    }

    @TestTargets({
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test equals",
            method = "equals",
            args = {java.lang.Object.class}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test hashCode",
            method = "hashCode",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test describeContents",
            method = "describeContents",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test writeToParcel",
            method = "writeToParcel",
            args = {android.os.Parcel.class, int.class}
        )
    })
    public void testTools() {
        Signature byteSignature = new Signature(mSignatureByteArray);
        Signature stringSignature = new Signature(mSignatureString);

        // Test describeContents, equals
        assertEquals(0, byteSignature.describeContents());
        assertTrue(byteSignature.equals(stringSignature));

        // Test hashCode
        byteSignature = new Signature(mDiffByteArray);
        assertNotSame(byteSignature.hashCode(), stringSignature.hashCode());

        // Test writeToParcel
        Parcel p = Parcel.obtain();
        byteSignature.writeToParcel(p, 0);
        p.setDataPosition(0);
        Signature signatureFromParcel = Signature.CREATOR.createFromParcel(p);
        assertTrue(signatureFromParcel.equals(byteSignature));
        p.recycle();
    }
}
