package com.biokack.bacteriofag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class MainUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		setSizeFull();

		VerticalLayout mainVL = new VerticalLayout();
		setContent(mainVL);

		mainVL.setWidth(100, Unit.PERCENTAGE);
		mainVL.setHeightUndefined();
		mainVL.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		mainVL.addComponent(new Label("<h1>Faqs Finders</h1>", ContentMode.HTML));

		class FileReceiver implements Receiver, SucceededListener {
			private static final long serialVersionUID = -1276759102490466761L;

			public File file;

			public OutputStream receiveUpload(String filename, String mimeType) {
				// Create upload stream
				FileOutputStream fos = null; // Stream to write to
				try {
					// Open the file for writing.
					fos = new FileOutputStream(file);
				} catch (final java.io.FileNotFoundException e) {
					new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE)
							.show(Page.getCurrent());
					return null;
				}
				return fos; // Return the output stream to write to
			}

			public void uploadSucceeded(SucceededEvent event) {
				new Notification("Succeess", "Upload complete", Notification.Type.ERROR_MESSAGE)
						.show(Page.getCurrent());
			}
		}
		;
		FileReceiver receiver = new FileReceiver();

		final Upload upload = new Upload("Upload biofag", receiver);
		upload.setImmediateMode(false);
		upload.setButtonCaption("Upload File");
		upload.addSucceededListener(receiver);

		mainVL.addComponent(upload);

	}
}