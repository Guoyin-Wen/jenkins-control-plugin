package org.codinjutsu.tools.jenkins.logic;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import org.codinjutsu.tools.jenkins.JenkinsToolWindowFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
@Service
public class JenkinsNotifier {

    private final NotificationGroup jenkinsGroup = NotificationGroup.toolWindowGroup("Jenkins Notification",
            JenkinsToolWindowFactory.JENKINS_BROWSER);
    @Nullable
    private final Project project;

    JenkinsNotifier(@Nullable Project project) {
        this.project = project;
    }

    @NotNull
    public static JenkinsNotifier getInstance(@NotNull Project project) {
        JenkinsNotifier jenkinsNotifier = ServiceManager.getService(project, JenkinsNotifier.class);
        return jenkinsNotifier == null ? new JenkinsNotifier(project) : jenkinsNotifier;
    }

    @NotNull
    public Notification error(@NlsContexts.NotificationContent String content) {
        return error(project, content);
    }

    @NotNull
    public Notification error(@Nullable Project project, @NlsContexts.NotificationContent String content) {
        return notify(project, content, NotificationType.ERROR);
    }

    @NotNull
    public Notification warning(@NlsContexts.NotificationContent String content) {
        return notify(project, content, NotificationType.WARNING);
    }

    @NotNull
    public Notification warning(@Nullable Project project, @NlsContexts.NotificationContent String content) {
        return notify(project, content, NotificationType.WARNING);
    }

    @NotNull
    public Notification notify(@NlsContexts.NotificationContent String content, NotificationType notificationType) {
        return notify(project, content, notificationType);
    }

    @NotNull
    public Notification notify(@Nullable Project project, @NlsContexts.NotificationContent String content, NotificationType notificationType) {
        final Notification notification = jenkinsGroup.createNotification(content, notificationType);
        notification.setListener(NotificationListener.URL_OPENING_LISTENER);
        notification.notify(project);
        return notification;
    }
}
